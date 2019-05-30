package com.bfd.api.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.mybatis.mapper.LabelCommonMapper;
import com.bfd.api.mybatis.mapper.LabelConfigMapper;
import com.bfd.api.repository.KHanRepository;
import com.bfd.api.service.TagService;
import com.bfd.api.utils.ESUtils;
import com.bfd.api.utils.LogUtils;
import com.bfd.api.vo.CommonLabelVo;
import com.bfd.api.vo.LabelVo;
import com.bfd.api.vo.TagTerm;
import com.bfd.api.vo.UserInfo;
import com.bfd.api.vo.UserInfo_SMS;
import com.netflix.client.http.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tagService")
@Transactional
public class TagServiceImpl implements TagService {
	
	@Autowired
	private LabelConfigMapper labelConfigMapper;
	@Autowired
	private LabelCommonMapper labelCommonMapper;

	
	@Override
	public long getUserNum(String userId,
			String userLogo, String userProject,String accessLogo,String accessProject, String grouping) throws Exception {
		long start = new Date().getTime();
		List<String> queryList = getQueryStr(grouping);
		long end = new Date().getTime();
		LogUtils.info((end-start)+"ms 查找用户群MYSQL执行时间");
		if(queryList==null || queryList.size()==0){
			throw new Exception("grouping is error");
		}
		long num = queryKhanNum(userId,userLogo, userProject,accessLogo,accessProject,queryList);
		return num;
	}

	@Override
	public PageInfo<UserInfo> getUserList(String userId,
			String userLogo, String userProject,
			String accessLogo,
			String accessProject,String grouping,String appCode,
			Integer currentPage, Integer pageSize) throws Exception {
		long start = new Date().getTime();
		List<String> queryList = getQueryStr(grouping);
		long end = new Date().getTime();
		LogUtils.info((end-start)+"ms 查找用户群MYSQL执行时间");
		if(queryList==null || queryList.size()==0){
			throw new Exception("grouping is error");
		}
		
		PageInfo<UserInfo> page = new PageInfo<UserInfo>(currentPage,pageSize);
		Integer pageNo = page.getPageNo();
		//查询khan
		List<UserInfo> list = queryKhan(userId,userLogo, userProject,accessLogo,accessProject,queryList,pageNo,pageSize);
		page.setTotalList(list);
		
		//记录数据使用信息
		if(list!=null && list.size()>0){
			start = new Date().getTime();
			KHanRepository khan = new KHanRepository();
			boolean flag = khan.bulkInsert(list, userId, userLogo, userProject, appCode);
			if(!flag){
				throw new Exception("add user access log error");
			}
			end = new Date().getTime();
			LogUtils.info((end-start)+"ms 查找用户群数量插入KHAN执行时间");
		}
		return page;
	}
	
	

	private List<UserInfo> queryKhan(String userId,String userLogo, String userProject,String accessLogo,String accessProject, List<String> queryList,Integer pageNo,Integer pageSize)  throws Exception {
		KHanRepository khan = new KHanRepository();
		List<UserInfo> list = khan.selectUserList(userId,userLogo,userProject,accessLogo,accessProject,queryList,pageNo,pageSize);
		return list;
	}
	
	private long queryKhanNum(String userId,String userLogo, String userProject,String accessLogo,String accessProject, List<String> queryList)  throws Exception {
		KHanRepository khan = new KHanRepository();
		long num = khan.selectUserCount(userId,userLogo,userProject,accessLogo,accessProject,queryList);
		return num;
	}
	
	public List<TagTerm> getQuery(String grouping) throws Exception {
		String[] ids = grouping.split("\\|");
		List<LabelVo> labelLists = labelConfigMapper.selectByLabelId(ids);
		List<TagTerm> list = new ArrayList<TagTerm>();
		for(LabelVo config:labelLists){
			TagTerm term = new TagTerm(config.getHbaseColumn(),config.getLabelName());
			list.add(term);
		}
		return list;
	}
	
	public List<String> getQueryStr(String grouping) throws Exception {
		String[] ids = grouping.split("\\|");
		List<LabelVo> labelLists = labelConfigMapper.selectByLabelId(ids);
		List<String> list = new ArrayList<String>();
		for(LabelVo config:labelLists){
			list.add(config.getHbaseColumn()+"_"+config.getLabelName());
		}
		return list;
	}
	
	/**
	 * TODO 此处后面需要修改，只能查一个业态下面的标签
	 * @param termMap
	 * @return
	 * @throws Exception
	 */
	public BoolQueryBuilder query(Map<String,List<TagTerm>> termMap) throws Exception {
		BoolQueryBuilder boolBuilder =  QueryBuilders.boolQuery();
		for(Map.Entry<String, List<TagTerm>> map : termMap.entrySet()){
			BoolQueryBuilder builder =  QueryBuilders.boolQuery();
			List<TagTerm> condition = map.getValue();
			for(TagTerm term:condition){
				builder.must(QueryBuilders.matchQuery(term.getColumn(), term.getValue()));
			}
			NestedQueryBuilder queryBuilder = QueryBuilders.nestedQuery(map.getKey(), builder);
			boolBuilder.must(queryBuilder);
		}
		return boolBuilder;
	}
	
	public static int getStart(int pageNo,int pageSize) {
		  if(pageSize==0){
	            pageSize=15;
	        }
		  if(pageNo<1){
			  pageNo=1;
		  }
	    return (pageNo-1)*pageSize;
	}

	public List<UserInfo> queryKhan(String userLogo, String userProject){
		List<UserInfo> list = new ArrayList<UserInfo>();
		for(int i=0;i<10;i++){
			UserInfo log = new UserInfo();
			log.setSuperid("cust"+i);
			log.setFormatLogo("my");
			log.setProject("pj101");
			log.setAge("23");
			log.setCity("beijing");
			log.setGender("0");
			log.setMobile("123455666");
			list.add(log);
		}
		return list;
	}

	
	
	
	
	
	
	 public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }    
	
	
	
	
	/*
	 * 该方法获取公共接口的人员长度
	 * 
	 * */
	@Override
	public Long getUserNumCommon(Long appId, String json) throws Exception {
		String table = "user_label_info_"+appId;
		String line = "user_label_info_"+appId+"_";
		char[] sql_source = JSONObject.fromObject(json).get("json").toString().toCharArray();
		String[] params_source = JSONObject.fromObject(json).get("params").toString().split(",");
		ArrayList<CommonLabelVo> params_new1 = labelCommonMapper.selectByLabelId(params_source);
		HashMap<String, String> hash = new HashMap<>();
		for (CommonLabelVo commonLabelVo : params_new1) {
			hash.put(commonLabelVo.getId()+"", commonLabelVo.getCode());
		}
		ArrayList<String> params_new = new ArrayList<>();
		for (String string : params_source) {
			params_new.add((hash.get(string)==null?string:hash.get(string).replace(":", "\\:")));
			if(hash.get(string)==null&&!string.startsWith("0")){
					throw new Exception("params is error");
			}
		}
		
		//代表的是?的数量
		int a2 = 0;
		String sql = "select count(*) as num from " + table + " where ";
		for(int i=0;i<sql_source.length;i++){
			if(sql_source[i]=='('){
				sql = sql + " ( ";
			}else if(sql_source[i]=='*'){
				String line1 = "";
				//获取查询列名称
				if(params_new.get(a2).startsWith("0")){
					line1=line+params_new.get(a2).substring(0, 2);
				}else{
					line1="taginfo";
				}
				sql = sql + line1+"='"+params_new.get(a2)+"'";
				a2++;
			}else if(sql_source[i]=='!'){
				sql = sql + " or ";
			}else if(sql_source[i]=='_'){
				sql = sql + " and ";
			}else if(sql_source[i]==')'){
				sql = sql + " )" ;
			}else{
				throw new Exception("json is error");
			}
		}
		//superid存在
		sql = org.nlpcn.es4sql.Test.sqlToEsQuery(sql).replace("?", "_");
		System.out.println(sql);
		//superid存在
		Long num = 0l ;
		//查ES 
		String ips = "http://10.0.6.109:9200/"+table+"/test/_search";
		String result = sendPost(ips, sql);
	    System.out.println(result);
	    num =JSONObject.fromObject(JSONObject.fromObject(result).get("hits")).getLong("total");
	    return num;
	}
	
	
	@Test
	public void ma(){
		String result ="{\"hits\":{\"total\":1234}}";
		System.out.println();
	}
	
	
	/**
	 * 该方法是为了解析json串数据形成的通用接口方法
	 * ((业态or业态) and (省份or省份) and (市or市) and (县or县) and (项目or项目) and (几线城市or几线城市) and (标签) and (标签) and (标签))
	mobile  全部业态、全部省、全部市、全部县、全部项目、全部几线城市 在没有业态标签查找的时候是OK的，在有业态查找的时候了？
	首先根据  业态和省份等这些条件进行了筛选之后  对标签筛选的话，就直接进行 or操作就可以了。
	 */
	@Override
	public PageInfo<UserInfo> getUserListCommon(Long appId, String json, Integer currentPage, Integer pageSize,Long num) throws Exception {
		
		String table = "user_label_info_"+appId;
		String line = "user_label_info_"+appId+"_";
		
		char[] sql_source = JSONObject.fromObject(json).get("json").toString().toCharArray();
		String[] params_source = JSONObject.fromObject(json).get("params").toString().split(",");
		ArrayList<CommonLabelVo> params_new1 = labelCommonMapper.selectByLabelId(params_source);
		HashMap<String, String> hash = new HashMap<>();
		for (CommonLabelVo commonLabelVo : params_new1) {
			hash.put(commonLabelVo.getId()+"", commonLabelVo.getCode());
		}
		ArrayList<String> params_new = new ArrayList<>();
		//存放业态标签值
		ArrayList<String> params_1 = new ArrayList<>();
		//存放查询值
		ArrayList<String> params_2 = new ArrayList<>();
		ArrayList<String> params_3 = new ArrayList<>();
		ArrayList<String> params_4 = new ArrayList<>();
		ArrayList<String> params_5 = new ArrayList<>();
		ArrayList<String> params_6 = new ArrayList<>();
		ArrayList<String> params_7 = new ArrayList<>();
		
		for (String string : params_source) {
			params_new.add((hash.get(string)==null?string:hash.get(string)));
			if(hash.get(string)==null&&!string.startsWith("0")){
					throw new Exception("params is error");
			}
		}
		
		int pagesize1 = pageSize;
		
		if(((int)(num/pageSize)+1)==currentPage){
			pageSize = (int) (num - (currentPage-1)*pageSize);
		}
		
		//返回对象值
		PageInfo<UserInfo> page = new PageInfo<UserInfo>(currentPage,pageSize,Integer.valueOf(num+""),pagesize1==pageSize?1:0);
		Integer pageNo =(currentPage-1)*pagesize1;
		
		//代表的是)的数量
		//代表的是?的数量
		int a2 = 0;
		String sql = "select mobile from " + table + " where ";
		for(int i=0;i<sql_source.length;i++){
			if(sql_source[i]=='('){
				sql = sql + " ( ";
			}else if(sql_source[i]=='*'){
				String line1 = "";
				//获取查询列名称
				if(params_new.get(a2).startsWith("0")){
					line1=line+params_new.get(a2).substring(0, 2);
					if(params_new.get(a2).startsWith("00")){
						params_2.add(params_new.get(a2));						
					}
					if(params_new.get(a2).startsWith("01")){
						params_3.add(params_new.get(a2));						
					}
					if(params_new.get(a2).startsWith("02")){
						params_4.add(params_new.get(a2));
					}
					if(params_new.get(a2).startsWith("03")){
						params_5.add(params_new.get(a2));
					}
					if(params_new.get(a2).startsWith("04")){
						params_6.add(params_new.get(a2));
					}
					if(params_new.get(a2).startsWith("05")){
						params_7.add(params_new.get(a2));
					}
				}else{
					if(params_new.get(a2).startsWith("futureland_real_estate")||params_new.get(a2).startsWith("business_management")||params_new.get(a2).startsWith("develop_of_xincheng")
							||params_new.get(a2).startsWith("futureland_property"))
					{
						line1="TAG";
						params_1.add(params_new.get(a2));
					}
					else line1="taginfo";
				}
				if(line1.equals("TAG")){
					sql = sql + line1 + "   " ;
				}else{
				sql = sql + line1+"='"+params_new.get(a2)+"'";
				}
				a2++;
			}else if(sql_source[i]=='!'){
				sql = sql + " or ";
			}else if(sql_source[i]=='_'){
				sql = sql + " and ";
			}else if(sql_source[i]==')'){
				sql = sql + " )" ;
			}else{
				throw new Exception("json is error");
			}
		}
		sql = sql + " order by mobile desc ";
		if(!(pageNo==0&&pageSize==0)){
			sql =sql + " limit " + pageNo+ " , " + pageSize ;
		}
		for(int i=0;i<params_1.size();i++){
			String sql1 = "(";
			
			if(params_2.size()==0&&params_3.size()==0&&params_4.size()==0&&params_5.size()==0&&params_6.size()==0&&params_7.size()==0){
				sql1 = sql1+"taginfo='"+params_1.get(i)+"' ";
			}else{
				String sql2 = "";
				sql2 = sql2 + "(";
			for(int j=0;j<params_2.size();j++){
				sql2 = sql2 + " taginfo =\'" +params_2.get(j)+params_1.get(i)+"' ";
				if(j!=params_2.size()-1){
					sql2 = sql2 + " or ";
				}
			}
			sql2 = sql2 + ")";
			
			
			String sql3 = "";
			sql3 = sql3 + "(";
		for(int j=0;j<params_3.size();j++){
			sql3 = sql3 + " taginfo =\'" +params_3.get(j)+params_1.get(i)+"' ";
			if(j!=params_3.size()-1){
				sql3 = sql3 + " or ";
			}
		}
		sql3 = sql3 + ")";
		
		
		
		String sql4 = "";
		sql4 = sql4 + "(";
	for(int j=0;j<params_4.size();j++){
		sql4 = sql4 + " taginfo =\'" +params_4.get(j)+params_1.get(i)+"' ";
		if(j!=params_4.size()-1){
			sql4 = sql4 + " or ";
		}
	}
	sql4 = sql4 + ")";
	
	
	
	String sql5 = "";
	sql5 = sql5 + "(";
for(int j=0;j<params_5.size();j++){
	sql5 = sql5 + " taginfo =\'" +params_5.get(j)+params_1.get(i)+"' ";
	if(j!=params_5.size()-1){
		sql5 = sql5 + " or ";
	}
}
sql5 = sql5 + ")";


String sql6 = "";
sql6 = sql6 + "(";
for(int j=0;j<params_6.size();j++){
sql6 = sql6 + " taginfo =\'" +params_6.get(j)+params_1.get(i)+"' ";
if(j!=params_6.size()-1){
	sql6 = sql6 + " or ";
}
}
sql6 = sql6 + ")";


String sql7 = "";
sql7 = sql7 + "(";
for(int j=0;j<params_7.size();j++){
sql7 = sql7 + " taginfo =\'" +params_7.get(j)+params_1.get(i)+"' ";
if(j!=params_7.size()-1){
	sql7 = sql7 + " or ";
}
}
sql7 = sql7 + ")";
			
ArrayList<String> sqllist = new ArrayList<String>();
if(!sql2.equals("()"))sqllist.add(sql2);
if(!sql3.equals("()"))sqllist.add(sql3);			
if(!sql4.equals("()"))sqllist.add(sql4);
if(!sql5.equals("()"))sqllist.add(sql5);
if(!sql6.equals("()"))sqllist.add(sql6);
if(!sql7.equals("()"))sqllist.add(sql7);
String sql11 = "";
for(int m=0;m<sqllist.size();m++){
	sql11 = sql11 + sqllist.get(m);
	if(m!=sqllist.size()-1){
		sql11 = sql11 + " and ";
	}
}

sql1 = sql1 + sql11;
}
			sql1 = sql1 + ") ";
			sql=sql.replaceFirst("TAG", sql1);
		}
		

		System.out.println("sql="+sql);
		
		sql = org.nlpcn.es4sql.Test.sqlToEsQuery(sql).replace("?", "_");
		
		//superid存在
		//使用的是TransportClient操作性能好一点。
		long begin = new Date().getTime();
		List<UserInfo_SMS> mobileList  = null;
		List<UserInfo_SMS> mobileList1  = null;
		mobileList1 = ESUtils.SMS_FIND(table, "test", sql, "mobile");
     	mobileList = mobileList1; 

		try{
		writeFile(appId,pageSize,mobileList);
		}catch(Exception e){
			System.out.println("当前查询数据不存在");
		}
		page.setTotalList(mobileList,1);
		LogUtils.info((new Date().getTime()-begin)+"ms 222222");
		return page;
		//注意操作的时候设置size的大小操作问题，
		//server.max-http-header-size=2097152  在application.properties中进行操作
		
		//第二种方式使用sql转化成dsl直接进行post请求   第三种方式使用druid数据源不稳定，应该是自己没搞清楚，日后再看。
//		sql = org.nlpcn.es4sql.Test.sqlToEsQuery(sql).replace("?", "_");
//		System.out.println(sql);
//		String ips = "http://10.0.6.109:9200/"+table+"/test/_search";
//		long begin = new Date().getTime();
//		String result = sendPost(ips, sql);
//		LogUtils.info((new Date().getTime()-begin)+"ms 111111");
//		List<UserInfo_SMS> list = new ArrayList<UserInfo_SMS>();
//		com.alibaba.fastjson.JSONArray jsonarray = JSON.parseObject(result).getJSONObject("hits").getJSONArray("hits");
//		LogUtils.info((new Date().getTime()-begin)+"ms 1111111111");
//		for(int j=0;j<jsonarray.size();j++){
//		UserInfo_SMS userInfo = new UserInfo_SMS();
//		userInfo.setMobile(jsonarray.getJSONObject(j).getJSONObject("_source").getString("mobile"));
//		list.add(userInfo);
//		}
//		LogUtils.info((new Date().getTime()-begin)+"ms 222222");
//		page.setTotalList(list,1);
//		try{
//			ESUtils.aa();
//		}catch(Exception e){
//			System.out.println("nonononono");
//		}
//		return page;
	}
	
	
	public void writeFile(Long appId, int num,List<UserInfo_SMS> mobileList) throws IOException{
		StringBuffer mobileArray = new StringBuffer();
		for(UserInfo_SMS userInfo_sms:mobileList){
			mobileArray.append(userInfo_sms.getMobile() + ",");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String getTime = df.format(new Date());
		String finalText = appId + "|" + getTime + "|" + num + "|" + mobileArray.substring(0, mobileArray.length()-1).toString();
 		File outFile = new File("/tmp/massage/file.txt");
        if(!outFile.exists()){
         outFile.createNewFile();
        }  
//	    OutputStreamWriter ow=new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8");
//	    ow.write(finalText + "/n");
//	    ow.close();
		 FileWriter writer = null;  
	        try {     
	            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
	            writer = new FileWriter(outFile, true);     
	            writer.write(finalText + "\n");       
	        } catch (IOException e) {     
	            e.printStackTrace();     
	        } finally {     
	            try {     
	                if(writer != null){  
	                    writer.close();     
	                }  
	            } catch (IOException e) {     
	                e.printStackTrace();     
	            }     
	        }   
	    }     
		
}

