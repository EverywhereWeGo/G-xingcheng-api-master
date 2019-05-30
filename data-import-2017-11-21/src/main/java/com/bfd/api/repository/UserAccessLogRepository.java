package com.bfd.api.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.bfd.api.utils.CalendarUtil;
import com.bfd.api.utils.Constants;
import com.bfd.api.utils.RedisUtil;
import com.bfd.api.vo.UserAccessInfo;
import com.bfd.api.vo.UserAccessLog;
import com.google.common.collect.Maps;

/**
 * 客户数据访问记录表
 * 
 * @author BFD_523
 *
 */
public class UserAccessLogRepository extends HbaseRepository {

	private static Table accessLogTable;
	private static Table accessLogoTable;

	static {
		accessLogTable = getTable(Constants.HBASE_USER_ACCESS_LOG);
		accessLogoTable = getTable(Constants.HBASE_USER_ACCESS_LOGO);
	}

	/**
	 * 获取用户使用情况
	 * 
	 * @throws Exception
	 */
	public static UserAccessInfo get(String custid) throws Exception {

		String rowkey = getRowkey(custid);
		Get get = new Get(Bytes.toBytes(rowkey));
		Result logo = accessLogoTable.get(get);
		// 数据未引用过
		if (logo == null || logo.rawCells() == null) {
			return null;
		}

		// 数据引用过
		Cell[] cells = logo.rawCells();
		List<Get> rklist = new ArrayList<Get>();
		for (Cell cell : cells) {
			String rk = new String(CellUtil.cloneValue(cell));
			Get g = new Get(Bytes.toBytes(rk));
			rklist.add(g);
		}

		UserAccessInfo info = new UserAccessInfo(custid);
		List<String> accessLogo = new ArrayList<String>();
		List<String> accessPorject = new ArrayList<String>();
		Result[] rs = accessLogTable.get(rklist);
		List<UserAccessLog> list = new ArrayList<UserAccessLog>();
		for (Result r : rs) {
			Cell[] cels = r.rawCells();
			Map<String, String> map = Maps.newHashMap();
			for (Cell cell : cels) {
				String key = new String(CellUtil.cloneQualifier(cell));
				map.put(key, new String(CellUtil.cloneValue(cell), "UTF-8"));
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			UserAccessLog log = (UserAccessLog) JSONObject.toBean(jsonObject,
					UserAccessLog.class);
			list.add(log);
			accessLogo.add(log.getAccessLogo());
			accessPorject.add(log.getAccessProject());
			info.setCustLogo(log.getCustLogo());
			info.setCustProject(log.getCustProject());
		}

		info.setList(list);
		info.setAccessLogo(accessLogo);
		info.setAccessProject(accessPorject);
		return info;
	}

	/**
	 * 更新用户访问记录----新增
	 * @throws Exception 
	 */
	public static void bulkUpdate(List<Map<String, Object>> userlist,
			String userId, String userLogo, String userProject,String appCode) throws Exception {

		List<UserAccessLog> loglist = new ArrayList<UserAccessLog>();
		//mid table
		List<Put> midlist = new ArrayList<Put>();
		for (Map<String, Object> map : userlist) {
			// 新增日志表
			UserAccessLog accessLog = new UserAccessLog();
			accessLog.setAccessId(userId);
			accessLog.setAccessLogo(userLogo);
			accessLog.setAccessProject(userProject);
			accessLog.setAppCode(appCode);
			if (map.get("mobile") != null) {
				accessLog.setCustId(map.get("mobile") + "");
			}

			if (map.get("formatLogo") != null) {
				accessLog.setCustLogo(map.get("formatLogo") + "");
			}

			if (map.get("project") != null) {
				accessLog.setCustProject(map.get("project") + "");
			}
			accessLog.setStatus(Constants.ACCESS_ON);
			accessLog.setCdate(CalendarUtil.getDefaultDateString(new Date()));
			
			String midrkey = getRowkey(map.get("mobile")+"");
			String logrKey = getRowkey(accessLog.getCustId()+":"+accessLog.getAccessLogo()+":"+accessLog.getAccessProject());
			String column = accessLog.getAccessLogo()+"_"+accessLog.getAccessProject();
			
			// 更新索引表
			Put put = new Put(Bytes.toBytes(getRowkey(midrkey)));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("rk"+column),Bytes.toBytes(logrKey));
			midlist.add(put);
		}
		
		//批量插入
		saveAccessLogBatch(loglist);
		midlist.addAll(midlist);
		
		//更新redis
		for (Map<String, Object> map : userlist) {
			String custid = map.get("mobile")+"";
			RedisUtil.del(Constants.REDIS_USER_DISREPEAT+"_"+custid);
			UserAccessInfo userinfo = get(custid);
			if(userinfo!=null){
				RedisUtil.set(Constants.REDIS_USER_DISREPEAT+"_"+custid, JSONObject.fromObject(userinfo).toString());
			}
		}
	}

	/**
	 * 删除用户访问记录----状态回写时使用
	 */
	public static void delete() {
		//更新hbase表状态
		//更新redis key
	}
	
	private static void saveAccessLogBatch(List<UserAccessLog> logs) throws Exception{
		for(UserAccessLog accessLog:logs){
			saveAccessLogTable(accessLog);
		}
	}

	private static void saveAccessLogTable(UserAccessLog accessLog) throws Exception{
		String rowKey = getRowkey(accessLog.getCustId()+":"+accessLog.getAccessLogo()+":"+accessLog.getAccessProject());
		Put put = new Put(Bytes.toBytes(rowKey));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cust_id"), 
				Bytes.toBytes(accessLog.getCustId()+""));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cust_logo"),Bytes.toBytes(accessLog.getCustLogo()+""));
		
		if(StringUtils.isNotBlank(accessLog.getCustProject())){
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cust_project"),Bytes.toBytes(accessLog.getCustProject()+""));
		}
		
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("status"), 
				Bytes.toBytes(accessLog.getStatus()+""));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cdate"), 
				Bytes.toBytes(accessLog.getCdate()+""));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("access_id"), 
				Bytes.toBytes(accessLog.getAccessId()+""));
		put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("access_logo"), 
				Bytes.toBytes(accessLog.getAccessLogo()+""));
		
		if(StringUtils.isNotBlank(accessLog.getAccessProject())){
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("access_project"), Bytes.toBytes(accessLog.getAccessProject()));
		}

		accessLogTable.put(put);
	}
}
