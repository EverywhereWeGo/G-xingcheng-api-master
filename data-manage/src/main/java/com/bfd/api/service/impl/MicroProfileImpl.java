package com.bfd.api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfd.api.domain.LabelCategory;
import com.bfd.api.domain.LabelConfig;
import com.bfd.api.domain.LabelInfo;
import com.bfd.api.mybatis.mapper.LabelCategoryMapper;
import com.bfd.api.mybatis.mapper.LabelConfigMapper;
import com.bfd.api.mybatis.mapper.LabelInfoMapper;
import com.bfd.api.service.MicroProfileService;
import com.bfd.api.utils.CalendarUtil;
import com.bfd.api.utils.CommonUtils;
import com.bfd.api.utils.ConstantsUtil;
import com.bfd.api.utils.HbaseUtil2;
import com.bfd.api.utils.LogUtils;
import com.bfd.api.utils.MD5UtilAdapter;
import com.bfd.api.utils.Pair;
import com.bfd.api.utils.StringUtil;

@Service("microProfileService")
public class MicroProfileImpl implements MicroProfileService {
	private JSONObject categoryCode2IDMapping = new JSONObject();
	private JSONObject categoryCode2NameMapping = new JSONObject();
	private JSONObject labelHbaseCol2Level3IDMapping = new JSONObject();
	@Autowired
	private LabelConfigMapper labelConfigMapper;
	@Autowired
	private LabelCategoryMapper labelCotegoryMapper;
	@Autowired
	private LabelInfoMapper labelInfoMapper;

	public String getGraph(String type, String key) {
		try {
			
			JSONObject resultJsonObj = new JSONObject();
			
			LogUtils.debug("Type:" + type + " Key:" + key);
			key = type + ":" + key;
			String cycle = "";
			// 判断接口不能为空
			if ((key == null) || (key.equals(""))) {
				LogUtils.error("微观接口必须参数为空!");
				resultJsonObj.put("code", Integer.valueOf(0));
				resultJsonObj.put("message", "请求参数为空");
				return resultJsonObj.toString();
			}
			String gid = "";
			String superId = "";
			// 获取SUPERID
			superId = transferMark2SuperID(key);
			LogUtils.debug("SUPERID:" + superId);
			if ((superId == null) || ("".equals(superId))) {
				LogUtils.error("无法拉通到superid! key  " + key);
				resultJsonObj.put("code", Integer.valueOf(0));
				resultJsonObj.put("message", "error");
				return resultJsonObj.toString();
			}
			initLabelMapping();
			// 根据superid获取gid
			gid = transferSuperID2GID(superId);
			LogUtils.debug("gid:" + gid);
			// 获取HBase表的名称
			String hbaseTableName = getHbaseTableNameByTimelimit(cycle);
			LogUtils.debug("hbaseTableName:" + hbaseTableName);
			// 获取标签权限
			Map<String, String> infos = HbaseUtil2.getData(hbaseTableName,
					superId, "up");
			LogUtils.debug("infos:" + JSON.toJSONString(infos));
			if ((superId == null) || ("".equals(superId))) {
				LogUtils.debug("[WARN]根据SUPERID查询V3表，无数据，rowkey：" + key);
			}
			JSONArray toReturnData = new JSONArray();
			for (Map.Entry<String, String> info : infos.entrySet()) {
				String fullColumn = (String) info.getKey();
				// 解析hbase取出的列
				JSONObject columnInfo = parseFullColumn(fullColumn);
				if (columnInfo != null) {
					String column = columnInfo.getString("column");
					String columnP1 = columnInfo.getString("columnP1");
					String columnP2 = columnInfo.getString("columnP2");
					String columnP3 = columnInfo.getString("columnP3");
					// 获取每个标签的具体信息
					JSONObject labelInfo = getLabelInfoByHbaseColumn(column,
							columnP1, columnP2, columnP3,
							this.categoryCode2IDMapping,
							this.labelHbaseCol2Level3IDMapping);
					if (labelInfo != null) {
						JSONArray innerValuesArr = null;
						try {
							JSONObject joo = JSONObject.parseObject(info
									.getValue());
							innerValuesArr = joo.getJSONArray(column);
						} catch (Exception e) {
							try {
								innerValuesArr = JSONArray.parseArray(info
										.getValue());
							} catch (Exception x) {
								LogUtils.error("微观画像数据异常,非标准json结构");
								continue;
							}
						}
						toReturnData.add(getOneLabelInfo(innerValuesArr,
								labelInfo));
					}
				}
			}
			resultJsonObj.put("code", Integer.valueOf(1));
			resultJsonObj.put("message", "success");
			resultJsonObj.put("data", toReturnData);
			resultJsonObj.put("SUPERID", superId);
			LogUtils.debug("toReturnData：" + toReturnData);
			addExtraInfo(resultJsonObj, superId);
			LogUtils.debug("resultJsonObj:" + resultJsonObj.toString());
			
			Map<String, Object> tagres = getGraphStr(resultJsonObj.toString(), 0);
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("code", Integer.valueOf(1));
			res.put("message", "success");
			res.put("data", tagres);
			return JSON.toJSONString(res);
		} catch (Exception ex) {
			ex.printStackTrace();
			JSONObject resultJsonObj = new JSONObject();
			resultJsonObj.put("code", Integer.valueOf(0));
			resultJsonObj.put("message", "system error");
			System.out.println(resultJsonObj);
			return resultJsonObj.toString();
		}
	}

	/**
	 * 初始化标签
	 */
	private void initLabelMapping() {
		List<LabelCategory> categoryList = labelCotegoryMapper.list();
		for (LabelCategory categoryInfo : categoryList) {
			String code = categoryInfo.getCode();
			String id = Long.toString(categoryInfo.getId());
			String name = categoryInfo.getName();
			this.categoryCode2IDMapping.put(code, id);
			this.categoryCode2NameMapping.put(code, name);
		}
		List<LabelConfig> labelList = labelConfigMapper.list();
		for (LabelConfig labelConfig : labelList) {
			String hbaseColumn = labelConfig.getHbaseColumn();
			String lv3ID = labelConfig.getThirdLevelId();
			this.labelHbaseCol2Level3IDMapping.put(hbaseColumn, lv3ID);
		}
	}

	private String getHbaseTableNameByTimelimit(String timelimit) {
		String hbaseTableName = ConstantsUtil.HBASE_V3_TABLE_NAME;
		if (timelimit != null) {
			if (timelimit.equals("7")) {
				hbaseTableName = "OfflineUserProfileV3_7";
			} else if (timelimit.equals("30")) {
				hbaseTableName = "OfflineUserProfileV3_30";
			}
		}
		return hbaseTableName;
	}

	private JSONObject getLabelInfoByHbaseColumn(String column,
			String columnP1, String columnP2, String columnP3,
			JSONObject categoryCode2IDMapping,
			JSONObject labelHbaseCol2Level3IDMapping) {
		String type = "";
		String labelID = "";
		String categoryID = "";
		if (CommonUtils.isCategory(column)) {
			type = "4";
			String categoryCode = "/" + columnP2.replace("|", "/");
			categoryID = categoryCode2IDMapping.getString(categoryCode);
			if ((categoryID == null) || (categoryID.equals(""))) {
				LogUtils.error("微观画像从配置表获取品类ID失败,categoryCode:" + categoryCode);
				return null;
			}
			if (!labelHbaseCol2Level3IDMapping.containsKey(column)) {
				LogUtils.error("微观画像从配置表获取labelID失败,type: " + type
						+ ",  hbase_column: " + column);
				return null;
			}
			String tempLabelID = labelHbaseCol2Level3IDMapping
					.getString(column);
			if (tempLabelID.length() < 10) {
				LogUtils.error("level3ID格式错误,level3ID:" + tempLabelID);
			}
			labelID = tempLabelID;
		} else {
			type = "2";
			if (!labelHbaseCol2Level3IDMapping.containsKey(column)) {
				LogUtils.error("微观画像从配置表获取labelID失败,type: " + type
						+ ",  hbase_column: " + column);
				return null;
			}
			labelID = labelHbaseCol2Level3IDMapping.getString(column);
		}
		JSONObject result = new JSONObject();
		result.put("type", type);
		result.put("labelID", labelID);
		result.put("categoryID", categoryID);
		return result;
	}

	private JSONObject parseFullColumn(String fullColumn) {
		if (StringUtil.countMatch(fullColumn, ":") != 3) {
			LogUtils.error("微观画像key异常,无法解析column,fullColumn：" + fullColumn);
			return null;
		}
		String column = fullColumn.substring(fullColumn.indexOf(":") + 1);
		Pattern pattern = Pattern.compile("(.+):(.+):(.+)");
		Matcher matcher = pattern.matcher(column);
		if (!matcher.matches()) {
			LogUtils.error("微观画像key异常,无法解析column:" + column);
		}
		String columnP1 = matcher.group(1);
		String columnP2 = matcher.group(2);
		String columnP3 = matcher.group(3);

		JSONObject ret = new JSONObject();
		ret.put("column", column);
		ret.put("columnP1", columnP1);
		ret.put("columnP2", columnP2);
		ret.put("columnP3", columnP3);
		return ret;
	}

	private JSONObject getOneLabelInfo(JSONArray innerValuesArr,
			JSONObject labelInfo) {
		String labelID = labelInfo.getString("labelID");
		String categoryID = labelInfo.getString("categoryID");
		JSONArray weightArr = new JSONArray();
		JSONArray valueArr = new JSONArray();
		for (Object obj : innerValuesArr) {
			JSONObject jsonObj = JSONObject.parseObject(obj.toString());
			weightArr.add(Double.valueOf(jsonObj.getDouble("weight")));
			valueArr.add(jsonObj.getString("value"));
		}
		JSONObject oneLabelInfo = new JSONObject();
		oneLabelInfo.put("labelID", labelID);
		if (!categoryID.equals("")) {
			oneLabelInfo.put("categoryID", categoryID);
		}
		oneLabelInfo.put("weight", weightArr);
		oneLabelInfo.put("value", valueArr);

		return oneLabelInfo;
	}

	public JSONObject addExtraInfo(JSONObject source, String superID) {
		JSONObject ids = new JSONObject();

		ArrayList<Pair<String, JSONArray>> allInfo = new ArrayList<Pair<String, JSONArray>>();
		allInfo.addAll(getRelationInfo(superID));
		for (Pair<String, JSONArray> oneInfo : allInfo) {
			ids.put(oneInfo.fst, oneInfo.snd);
		}
		source.put("ids", ids);
		return source;
	}

	private ArrayList<Pair<String, JSONArray>> getRelationInfo(String superID) {
		ArrayList<Pair<String, JSONArray>> result = new ArrayList<Pair<String, JSONArray>>();
		Map<String, String> mapper = ConstantsUtil.BQ0011_RELATION_HBASE_MAPPER;
		String supid = MD5UtilAdapter.addPrefix("global:superid:" + superID);
		try {
			Map<String, String> relationInfo = HbaseUtil2.getData(
					ConstantsUtil.HBASE_USER_INFO_TABLE_NAME, supid);
			for (String configKey : mapper.keySet()) {
				if (relationInfo.containsKey(configKey)) {
					JSONObject oneInfo = JSONObject.parseObject(relationInfo
							.get(configKey));
					JSONArray oneInfoKeyArr = new JSONArray();
					oneInfoKeyArr.addAll(oneInfo.keySet());
					result.add(new Pair<String, JSONArray>((String) mapper
							.get(configKey), oneInfoKeyArr));
				} else {
					result.add(new Pair<String, JSONArray>((String) mapper
							.get(configKey), new JSONArray()));
				}
			}
		} catch (Exception ex) {
			LogUtils.error("无法拉通到superid! rowkey  " + supid);
			return null;
		}
		return result;
	}

	private String transferMark2SuperID(String originID) {
		String rowkey = null;
		try {
			if (!originID.contains(":")) {
				return originID;
			}
			//relation_graph_ysc_2000=HBASE_USER_INFO_TABLE_NAME
			//b3:global:mobile:13776048552
			rowkey = MD5UtilAdapter.addPrefix("global:" + originID);
			Map<String, String> relationInfo = HbaseUtil2.getData(
					ConstantsUtil.HBASE_USER_INFO_TABLE_NAME, rowkey);
			if (relationInfo.containsKey("ids:superid")) {
				return (String) relationInfo.get("ids:superid");
			}
			return null;
		} catch (Exception ex) {
			LogUtils.error(ex.getMessage());
			LogUtils.error("无法拉通到superid! rowkey  " + rowkey);
			return null;
		}
	}

	private String transferSuperID2GID(String superid) throws Exception {
		try {
			String rowkey = MD5UtilAdapter.addPrefix("global:superid:"
					+ superid);
			Map<String, String> relationInfo = HbaseUtil2.getData(
					ConstantsUtil.HBASE_USER_INFO_TABLE_NAME, rowkey);
			if (relationInfo.containsKey("ids:global:gid")) {
				return (String) relationInfo.get("ids:global:gid");
			}
			return "";
		} catch (Exception ex) {
			LogUtils.warn("不能获取到GID根据SPIERID，" + ex.getMessage());
			return "";
		}
	}

	private Map<String, Object> getGraphStr(String str, Integer cycle) {
		Map<String, Object> result = new HashMap<String, Object>();
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject
				.parseObject(str);
		String root = "root";
		Map<String, List<Map<String, Object>>> dataMap = new HashMap<String, List<Map<String, Object>>>();
		Map<String, Set<String>> cateMap = new HashMap<String, Set<String>>();
		if (!json.containsKey("data") || json.getString("data").isEmpty()) {
			return result;
		}

		com.alibaba.fastjson.JSONArray dataArr = json.getJSONArray("data");
		LogUtils.debug("dataArr:" + JSON.toJSONString(dataArr));
		for (int idx = 0; idx < dataArr.size(); idx++) {
			JSONObject data = dataArr.getJSONObject(idx);
			String firstLevelLabelId = null;
			String secondLevelLabelId = null;
			String categoryId = null;
			String firstLabelName = null;
			String secondLabellName = null;
			String thirdLabelName = null;
			String fourthLabelName = null;
			String fifthLabelName = null;
			String firstLabelNameKey = null;
			String secondLabellNameKey = null;
			String thirdLabelNameKey = null;
			String fourthLabelNameKey = null;
			String fifthLabelNameKey = null;
			String leafLabelNameKey = null;
			String labelId = data.getString("labelID");
			// 前五位为一级标签id
			firstLevelLabelId = labelId.substring(0, 5);
			LabelInfo firstLabel = labelInfoMapper
					.getLabelInfoByLabelId(firstLevelLabelId);

			if (firstLabel == null) {
				continue;
			}
			LogUtils.debug("firstLabel:" + firstLabel.getLabelName());
			firstLabelName = firstLabel.getLabelName();
			firstLabelNameKey = firstLabelName;
			LogUtils.debug("firstLabel.getDataFormat():"
					+ firstLabel.getDataFormat());
			if (firstLabel.getDataFormat() == null
					|| firstLabel.getDataFormat() == -1) {
				secondLevelLabelId = labelId.substring(0, 10);
				LogUtils.debug("secondLevelLabelId:" + secondLevelLabelId);
				LabelInfo secondLabel = labelInfoMapper
						.getLabelInfoByLabelId(secondLevelLabelId);
				LabelInfo thirdLabel = labelInfoMapper
						.getLabelInfoByLabelId(labelId);
				LogUtils.debug("secondLabel:" + secondLabel);
				LogUtils.debug("thirdLabel:" + thirdLabel.getLabelName());
				// 一二三级为空则过滤掉
				if (secondLabel == null || thirdLabel == null) {
					continue;
				}
				LogUtils.debug("secondLabel:" + secondLabel.getLabelName());
				LogUtils.debug("thirdLabel:" + thirdLabel.getLabelName());
				secondLabellName = secondLabel.getLabelName();
				thirdLabelName = thirdLabel.getLabelName();
				secondLabellNameKey = firstLabelNameKey + "-"
						+ secondLabellName;
				thirdLabelNameKey = secondLabellNameKey + "-" + thirdLabelName;
			} else {
				LabelInfo thirdLabel = labelInfoMapper
						.getLabelInfoByLabelId(labelId);
				LabelCategory category = labelCotegoryMapper
						.getCategoryById(thirdLabel.getCategoryId().toString());
				// 电商或媒体没有对应品类
				if (category == null)
					continue;
				String catePath = category.getPath();
				String cateName = category.getName();
				String labelName = thirdLabel.getLabelName();
				if (labelName.equals("品牌"))
					thirdLabelName = "品牌";
				else
					thirdLabelName = "属性";
				if (catePath.length() == 1)
					secondLabellName = cateName;
				else
					secondLabellName = catePath.split("/")[1];
				fourthLabelName = catePath + "/" + cateName;
				fourthLabelName = fourthLabelName.replace("/", "-")
						.substring(1);
				fifthLabelName = labelName;
				secondLabellNameKey = firstLabelNameKey + "-"
						+ secondLabellName;
				thirdLabelNameKey = secondLabellNameKey + "-" + thirdLabelName;
				fourthLabelNameKey = thirdLabelNameKey + "-" + fourthLabelName;
				fifthLabelNameKey = fourthLabelNameKey + "-" + fifthLabelName;
				if (data.containsKey("categoryid")) {
					categoryId = data.getString("categoryid");
					Set<String> cateSet = new HashSet<String>();
					if (cateMap.containsKey(firstLevelLabelId))
						cateSet = cateMap.get(firstLevelLabelId);
					cateSet.add(thirdLabel.getCategoryId().toString());
					cateMap.put(firstLevelLabelId, cateSet);
				}
			}

			List<Map<String, Object>> list = null;
			Map<String, Object> map = new HashMap<String, Object>();
			if (dataMap.containsKey("root")) {
				list = dataMap.get("root");
			} else {
				list = new ArrayList<Map<String, Object>>();
			}

			map.put("labelName", firstLabelName);
			map.put("key", firstLabelNameKey);
			map.put("dataFormat",
					(firstLabel.getDataFormat() == null ? -1 : 0) == -1 ? 0 : 1);
			if (!list.contains(map)) {
				list.add(map);
				dataMap.put("root", list);
			}
			LogUtils.debug("dataMap:" + JSON.toJSONString(dataMap));
			// 二级
			if (dataMap.containsKey(firstLabelNameKey)) {
				list = dataMap.get(firstLabelNameKey);
			} else {
				list = new ArrayList<Map<String, Object>>();
			}
			map = new HashMap<String, Object>();
			map.put("labelName", secondLabellName);
			map.put("key", secondLabellNameKey);
			if (!list.contains(map)) {
				list.add(map);
				dataMap.put(firstLabelNameKey, list);
			}
			// 三级
			if (dataMap.containsKey(secondLabellNameKey)) {
				list = dataMap.get(secondLabellNameKey);
			} else {
				list = new ArrayList<Map<String, Object>>();
			}
			leafLabelNameKey = thirdLabelNameKey;
			map = new HashMap<String, Object>();
			map.put("labelName", thirdLabelName);
			map.put("key", thirdLabelNameKey);
			if (!list.contains(map)) {
				list.add(map);
				dataMap.put(secondLabellNameKey, list);
			}
			// 四级
			if (fourthLabelName != null) {
				if (dataMap.containsKey(thirdLabelNameKey)) {
					list = dataMap.get(thirdLabelNameKey);
				} else {
					list = new ArrayList<Map<String, Object>>();
				}
				leafLabelNameKey = fourthLabelNameKey;
				map = new HashMap<String, Object>();
				map.put("labelName", fourthLabelName);
				map.put("key", fourthLabelNameKey);
				if (!list.contains(map)) {
					list.add(map);
					dataMap.put(thirdLabelNameKey, list);
				}
			}
			// 五级
			if (fifthLabelName != null) {
				if (dataMap.containsKey(fourthLabelNameKey)) {
					list = dataMap.get(fourthLabelNameKey);
				} else {
					list = new ArrayList<Map<String, Object>>();
				}
				leafLabelNameKey = fifthLabelNameKey;
				map = new HashMap<String, Object>();
				map.put("labelName", fifthLabelName);
				map.put("key", fifthLabelNameKey);
				if (!list.contains(map)) {
					list.add(map);
					dataMap.put(fourthLabelNameKey, list);
				}
			}
			// 末级
			if (dataMap.containsKey(leafLabelNameKey)) {
				list = dataMap.get(leafLabelNameKey);
			} else {
				list = new ArrayList<Map<String, Object>>();
			}
			String valueStr = data.getString("value");
			if (valueStr.matches("\\[.*\\]")) {
				JSONArray valueArr = data.getJSONArray("value");
				JSONArray weightArr = data.getJSONArray("weight");
				for (int vidx = 0; vidx < valueArr.size(); vidx++) {
					map = new HashMap<String, Object>();
					map.put("labelName", valueArr.getString(vidx));
					map.put("weight", weightArr.getString(vidx));
					list.add(map);
				}
			} else {
				String weightStr = data.getString("weight");
				map = new HashMap<String, Object>();
				map.put("labelName", valueStr);
				map.put("weight", weightStr);
				list.add(map);
			}
			dataMap.put(leafLabelNameKey, list);

		}
		// 品类相关
		for (Entry<String, Set<String>> entry : cateMap.entrySet()) {
			String firstLevelLabelId = null;
			String secondLevelLabelId = null;
			String firstLabelName = null;
			String secondLabellName = null;
			String thirdLabelName = null;
			String fourthLabelName = null;
			String fifthLabelName = null;
			String firstLabelNameKey = null;
			String secondLabellNameKey = null;
			String thirdLabelNameKey = null;
			String fourthLabelNameKey = null;
			String fifthLabelNameKey = null;
			String leafLabelNameKey = null;
			firstLevelLabelId = entry.getKey();
			for (String categoryId : entry.getValue()) {
				LabelInfo firstLabel = labelInfoMapper
						.getLabelInfoByLabelId(firstLevelLabelId);
				LabelCategory category = labelCotegoryMapper
						.getCategoryById(categoryId);
				// 一级为空则过滤掉
				if (firstLabel == null || category == null)
					continue;
				firstLabelName = firstLabel.getLabelName();
				firstLabelNameKey = firstLabelName;
				String catePath = category.getPath();
				String cateName = category.getName();
				if (catePath.length() == 1)
					secondLabellName = cateName;
				else
					secondLabellName = catePath.split("/")[1];
				thirdLabelName = "品类";
				fourthLabelName = catePath + "/" + cateName;
				fourthLabelName = fourthLabelName.replace("/", "-")
						.substring(1);
				secondLabellNameKey = firstLabelNameKey + "-"
						+ secondLabellName;
				thirdLabelNameKey = secondLabellNameKey + "-" + thirdLabelName;
				fourthLabelNameKey = thirdLabelNameKey + "-" + fourthLabelName;

				List<Map<String, Object>> list = null;
				Map<String, Object> map = new HashMap<String, Object>();
				if (dataMap.containsKey("root")) {
					list = dataMap.get("root");
				} else {
					list = new ArrayList<Map<String, Object>>();
				}
				map.put("labelName", firstLabelName);
				map.put("key", firstLabelNameKey);
				map.put("dataFormat", 1);
				if (!list.contains(map)) {
					list.add(map);
					dataMap.put("root", list);
				}
				// 二级
				if (dataMap.containsKey(firstLabelNameKey)) {
					list = dataMap.get(firstLabelNameKey);
				} else {
					list = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				map.put("labelName", secondLabellName);
				map.put("key", secondLabellNameKey);
				if (!list.contains(map)) {
					list.add(map);
					dataMap.put(firstLabelNameKey, list);
				}
				// 三级
				if (dataMap.containsKey(secondLabellNameKey)) {
					list = dataMap.get(secondLabellNameKey);
				} else {
					list = new ArrayList<Map<String, Object>>();
				}
				leafLabelNameKey = thirdLabelNameKey;
				map = new HashMap<String, Object>();
				map.put("labelName", thirdLabelName);
				map.put("key", thirdLabelNameKey);
				if (!list.contains(map)) {
					list.add(map);
					dataMap.put(secondLabellNameKey, list);
				}
				// 四级
				if (fourthLabelName != null) {
					if (dataMap.containsKey(thirdLabelNameKey)) {
						list = dataMap.get(thirdLabelNameKey);
					} else {
						list = new ArrayList<Map<String, Object>>();
					}
					leafLabelNameKey = fourthLabelNameKey;
					map = new HashMap<String, Object>();
					map.put("labelName", fourthLabelName);
					map.put("key", fourthLabelNameKey);
					if (!list.contains(map)) {
						list.add(map);
						dataMap.put(thirdLabelNameKey, list);
					}
				}
				// 五级
				if (fifthLabelName != null) {
					if (dataMap.containsKey(fourthLabelNameKey)) {
						list = dataMap.get(fourthLabelNameKey);
					} else {
						list = new ArrayList<Map<String, Object>>();
					}
					leafLabelNameKey = fifthLabelNameKey;
					map = new HashMap<String, Object>();
					map.put("labelName", fifthLabelName);
					map.put("key", fifthLabelNameKey);
					if (!list.contains(map)) {
						list.add(map);
						dataMap.put(fourthLabelNameKey, list);
					}
				}
			}
		}
		// map转tree
		for (Map.Entry<String, List<Map<String, Object>>> entry : dataMap
				.entrySet()) {
			List<Map<String, Object>> smallTreeList = new ArrayList<Map<String, Object>>();
			smallTreeList = entry.getValue();
			int nodeListSize = smallTreeList.size();
			for (int i = 0; i < nodeListSize; i++) {
				Map<String, Object> map = smallTreeList.get(i);
				if (!map.containsKey("key"))
					continue;
				String labelName = map.get("key").toString();
				map.remove("key");
				List<Map<String, Object>> findList = dataMap.get(labelName);
				map.put("children", findList);
			}
		}
		LogUtils.debug(json.getInteger("code").toString());
		String end = CalendarUtil.getDateString(Calendar.getInstance(),
				"yyyy.MM.dd");
		if (cycle.intValue() == 1) {
			String begin = CalendarUtil
					.getDateString(
							CalendarUtil.getDateByBeforeDays(
									Calendar.getInstance(), 7), "yyyy.MM.dd");
			result.put("date", begin + "-" + end);
		} else if (cycle.intValue() == 3) {
			String begin = CalendarUtil.getDateString(CalendarUtil
					.getDateByBeforeDays(Calendar.getInstance(), 30),
					"yyyy.MM.dd");
			result.put("date", begin + "-" + end);
		}
		LogUtils.debug("_data:"
				+ JSONArray.parse(JSON.toJSONString(dataMap.get(root))));
		result.put("_data",
				JSONArray.parse(JSON.toJSONString(dataMap.get(root))));
		/*
		 * com.alibaba.fastjson.JSONObject info = json.getJSONObject("info"); if
		 * (info != null) { result.put("info", info); }
		 */
		String SUPERID = json.getString("SUPERID");
		if (SUPERID != null) {
			result.put("SUPERID", SUPERID);
		}
		LogUtils.debug(JSON.toJSONString("result:" + result));
		return result;

	}
}
