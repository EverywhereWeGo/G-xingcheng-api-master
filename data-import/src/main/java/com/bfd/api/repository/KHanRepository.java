package com.bfd.api.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bfd.api.utils.CalendarUtil;
import com.bfd.api.utils.KHanUtils;
import com.bfd.api.utils.LogUtils;
import com.bfd.api.vo.UserInfo;

public class KHanRepository {

	private static final Logger logger = LoggerFactory.getLogger(KHanRepository.class);
	
	private static final String TABLE_NAME = "tag.user_access_log";
	
	private static final String TABLE_PATH = "/tmp/khanload/";

	private Timer timer = new Timer();
	
	private String project_Logo ="10005";
	
	public boolean bulkInsert(List<UserInfo> list,
			String userId, String userLogo, String userProject,String appCode) throws Exception {

		if (list == null || list.size() == 0) {
			return true;
		}

		FileWriter fw = null;
		PrintWriter pw = null;
		
		File f = new File(TABLE_PATH+UUID.randomUUID()+".txt");
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			fw = new FileWriter(f, true);
			pw = new PrintWriter(fw);
			
			// 输出至文本 /tmp/khanload/load_uuid
			for (UserInfo log : list) {
				pw.println(log.getSuperid() + "\t" + log.getFormatLogo()  + "\t" + log.getProject() + "\t" 
						+ userId + "\t" + userLogo + "\t" + userProject 
						+ "\t" + appCode + "\t" + 1 + "\t" + CalendarUtil.getDefaultDateString(new Date())
						+ "\t" + CalendarUtil.getDefaultDateString(new Date()));
				pw.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				fw.flush();
				pw.close();
				fw.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		//load数据至khan
		long count = loadDataToHawq(TABLE_NAME, f);
		if(count<0){
			return false;
		}
		return true;
	}

	/**
	 * 通过copy 命令 load 数据到hawq
	 * 
	 * @param
	 */
	public long loadDataToHawq(String tableName, File path) throws Exception {
		timer.Start();
		String copySql = "copy " + tableName + " from STDIN";

		logger.info("load data '" + path.getAbsolutePath()
				+ "' to khan table '" + tableName + "', sql: " + copySql);
		long count = new KHanUtils().copyDataToKhan(copySql, path.getAbsolutePath());
		if (count < 0) {
			logger.info("load data '" + path.getAbsolutePath()
					+ "' to khan table '" + tableName + "' failed");
		} else {
			logger.info("load data for table '" + tableName
					+ "' sucess, use time: " + timer.End()
					+ "(ms), total row: " + count);
		}
		return count;
	}

	public static class Timer {
		private long start = 0;
		private long end = 0;

		public void Start() {
			start = System.currentTimeMillis();
			end = 0;
		}

		public long End() {
			end = System.currentTimeMillis();
			return end - start;
		}

		public long GetTime() {
			return end - start;
		}
	}
	
	//List<UserInfo> list = queryKhan(userLogo, userProject,accessLogo,accessProject,queryList,pageNo,pageSize);	
	public List<UserInfo> selectUserList(String userId,String userLogo, String userProject,String accessLogo,String accessProject, List<String> queryList,Integer pageNo,Integer pageSize)  throws Exception {
		List<UserInfo> userList = new LinkedList<UserInfo>();
		Connection conn = null;
		PreparedStatement stmt = null ;
		ResultSet rs = null;
		KHanUtils khan = new KHanUtils();
		
		try {
			conn = khan.getConn();
			//用于删除空字符串
			int tem_num1 = 0;
			//得到筛选标签数量
			HashSet<String> hs = new HashSet<String>(queryList);
			int querylistnum = hs.size();
			//得到筛选标签数据
			String para = "";
			for (String query : queryList) {
				para = para + "'" + query + "',";
			}
			para = para.substring(0, para.length() - 1);
			
			String accessLogosql = "";
			String accessProjectsql = "";
			
			//定义规则三的增加数量在in条件中
			HashSet<String> bzLogo = new HashSet<String>();
			
			//定义规则四筛选SQL
			String accessLogoSQL = "";
			String accessProjectSQL = "";
			// 将目标业态处理，用于规则四的判断
			String[] accessLogoList1 = accessLogo.split("\\|");
			for(int i=0;i<accessLogoList1.length;i++){
				if(accessLogoList1[i].equals("10001")||accessLogoList1[i].equals("10002")||accessLogoList1[i].equals("10003")||accessLogoList1[i].equals("10004")){
					accessLogoList1[i]="";
				}
			}
			List<String> accessLogoList = new ArrayList<>();
			
			for (String string : accessLogoList1) {
				if(!string.equals("")){
					accessLogoList.add(string);
				}
			}
			
			//将规则四的业态条件补全
			//当目标业态与员工一致，且无其他
			if(accessLogoList.size()==1&&accessLogoList.contains(userLogo)){
				accessLogoSQL="( b.cust_logo like '%" + userLogo
						+ "%')";
			}
			//删除原因是因为不同业态的时候只看项目号，所以才进行删除，若需要恢复即可
			//当目标业态与员工不一致，且无其他
//			if(accessLogoList.size()==1&&!accessLogoList.contains(userLogo)){
//				accessLogoSQL="( b.cust_logo not like '%" + userLogo
//						+ "%' and b.cust_logo like '%"+accessLogoList.get(0)+"%' )";
//				
//			}
//			//当目标业态与员工一致，含其他业态
//			if(accessLogoList.size()>1&&accessLogoList.contains(userId)){
//				accessLogoSQL+="( ";
//				for (String string : accessLogoList) {
//					accessLogoSQL+="  b.cust_logo like '%"
//							+ string+"%' and";
//				}
//				accessLogoSQL=accessLogoSQL.substring(accessLogoSQL.length()-3, accessLogoSQL.length());
//				accessLogoSQL+=")";
//				
//			}
//			//当目标业态与员工不一致，含其他业态
//			if(accessLogoList.size()>1&&!accessLogoList.contains(userId)){
//				accessLogoSQL+="(  b.cust_logo not like '%"+userLogo+"%' and (";
//				for (String string : accessLogoList) {
//					accessLogoSQL+="  b.cust_logo like '%"
//							+ string+"%' and";
//				}
//				accessLogoSQL=accessLogoSQL.substring(accessLogoSQL.length()-3, accessLogoSQL.length());
//				accessLogoSQL+="))";
//			}
			
			//将规则四的项目条件补全
			String[] accessProjectList1 = accessProject.split("\\|");
			List<String> accessProjectList = new ArrayList<>();
			Collections.addAll(accessProjectList, accessProjectList1);
			//将空字符串清除，因为其可能不存在
			for (String string : accessProjectList) {
				if ("".equals(string)) {
					tem_num1++;
				}
			}
			for (int i = 0; i < tem_num1; i++) {
				try {
					accessProjectList.remove(accessProjectList.indexOf(""));
				} catch (Exception e) {
				}
			}
			//讲规则四的项目条件补全
			//当目标项目与员工一致，且无其他
			if(accessProjectList.size()==1&&accessProjectList.contains(userProject)){
				accessProjectSQL=" ( b.project_id like '%"+userProject
						+"%')";
			}
			//当目标项目与员工一致，其他
			if(accessProjectList.size()==1&&!accessProjectList.contains(userProject)){
				accessProjectSQL=" ( b.project_id not like '%"+userProject
						+"%' and b.project_id like '%" + accessProjectList.get(0)
						+ "%')";
			}
			
			//当目标项目与员工不一致，且存在当前项目
			if(accessProjectList.size()>1&&accessProjectList.contains(userProject)){
				
				accessProjectSQL+="( ";
				for (String string : accessProjectList) {
					accessProjectSQL+="  b.project_id like '%"
							+ string+"%' and";
				}
				accessProjectSQL=accessProjectSQL.substring(0, accessProjectSQL.length()-3);
				accessProjectSQL+=")";
				
			}
			//当目标项目与员工不一致，不存在当前项目
			if(accessProjectList.size()>1&&!accessProjectList.contains(userProject)){
				
				accessProjectSQL+="(  b.project_id not like '%"+userProject+"%' and (";
				for (String string : accessProjectList) {
					accessProjectSQL+="  b.project_id like '%"
							+ string+"%' and";
				}
				accessProjectSQL=accessProjectSQL.substring(0, accessProjectSQL.length()-3);
				accessProjectSQL+="))";
				
			}
			
			//accessLogoSQL就是规则四的where条件,将where条件合并起来
			if(!accessProjectSQL.equals("")){
				accessLogoSQL=accessProjectSQL + (accessLogoSQL.equals("")?" ":" and ")+accessLogoSQL;
			}
			
			// or需要判断在添加
			//判断员工项目是否存在
			boolean link3 = !userProject.equals("");
			int usernum = 0;
			int usernum1 = 0;
			//同业态下的规则三
			if(accessLogoList.contains(project_Logo)){
			
			
			for(String string :queryList){
							string = string.substring(string.indexOf(":")+1, string.lastIndexOf(":"));
							if(!string.contains("|"))continue;
							bzLogo.add(" 'futureland_real_estate:"+string
									+ ":is_expired_protect_time_否',");
							usernum1++;
							if(string.endsWith(userProject)){
								usernum++;
							}
				}
			}
			
			int addnum = bzLogo.size();
			
			String in = "";
			
			if(usernum!=usernum1){
			for (String string : bzLogo) {
				in += string;
			}
			in = in.substring(0, in.length()-1);
			}else{
				addnum=0;
			}
			boolean addcount = addnum == 0;
			
			
			//用将空字符串的数据进行拼接sql
			for (int i = 0; i < accessLogoList.size(); i++) {
				accessLogosql += " cust_logo like '%" + accessLogoList.get(i) + "%' "
						+ (i == accessLogoList.size() - 1 ? "" : " and ");
			}
			for (int i = 0; i < accessProjectList.size(); i++) {
				accessProjectsql += " project_id like '%" + accessProjectList.get(i) + "%' "
						+ (i == accessProjectList.size() - 1 ? "" : " and ");
			}
			//最终判断目标与项条件是否存在
			boolean link4 = !accessLogosql.equals("");
			boolean link5 = !accessProjectsql.equals("");
			

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Calendar calendar = Calendar.getInstance();// 日历对象
			calendar.setTime(new Date());// 设置当前日期
			calendar.add(Calendar.MONTH, -1);// 月份减一
			String time = df.format(calendar.getTime()) + "";

			String gz4 = " and ("+accessLogoSQL+")";
			
//			+ (!addcount ? in : "")
			
			String sql = "select b.* from (" + " select superid from (" + " select superid FROM"
					+ " tag.user_label_info a WHERE" + " taginfo IN(" + para  + ") "
					+ (!addcount?" and superid not in (select superid from tag.user_label_info a where taginfo in ("+in+"))":"")
					+ " " // 判断是否为目标地产
					+ ")a  " + " group by superid having count(*)=" + (querylistnum )
					+ " )a inner join dw.cust_project_relation b on a.superid=b.superid"
					+ (link4 || link5 ? " where " : " ") + (link4 ? "(" + accessLogosql + ")" : " ")
					+ (link4 && link5 ? " and " : " ") + (link5 ? "(" + accessProjectsql + ")" : " ")
					+ (!accessLogoSQL.equals("") ? gz4 : "") 
					+ " and b.superid not in" + " ("
					+ " select cust_id from tag.user_access_log b" + " where udate>='" + time + "'  and status=1 "
					+ " and access_id='" + userId + "'" + " and access_logo='" + userLogo + "'"
					+ (link3 ? " and access_project='" + userProject + "'" : "") + " union "
					+ " select cust_id from tag.user_access_log b" + " where  udate>='" + time + "'  and status=1 "
					+ " and access_logo='" + userLogo + "'" + (link3 ? (" and access_project='" + userProject + "'") : "")
					+ " and cust_logo like '%" + userLogo + "%' group by cust_id having count(*)>=2)" + " limit " + pageSize
					+ " offset " + pageNo;
			
			LogUtils.info(sql);
			String begin = "set optimizer = 1";
			conn.prepareStatement(begin).execute();
			long start = new Date().getTime();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			long end = new Date().getTime();
			LogUtils.info((end-start)+"ms 查找用户群信息KHAN执行时间");
			while(rs.next()){
				UserInfo userInfo = new UserInfo();
				userInfo.setSuperid(rs.getString("superid"));
				userInfo.setName(rs.getString("cust_name"));
				userInfo.setMobile(rs.getString("mobile"));
				userInfo.setHome_tel(rs.getString("home_tel"));
				userInfo.setOffice_tel(rs.getString("office_tel"));
				userInfo.setGender(rs.getString("gender"));
				userInfo.setAge(rs.getString("age"));
				userInfo.setCity(rs.getString("city"));
				userInfo.setFormatLogo(rs.getString("cust_logo"));
				userInfo.setProject(rs.getString("project_id"));
				userList.add(userInfo);
			}
		} finally{
			khan.close(stmt, rs);
			khan.close();
		}
		return userList;
	}
	
	public long selectUserCount(String userId,String userLogo, String userProject,String accessLogo,String accessProject, List<String> queryList)  throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null ;
		ResultSet rs = null;
		KHanUtils khan = new KHanUtils();
		long num=0;
		
		try {
			conn = khan.getConn();
			//用于删除空字符串
			int tem_num1 = 0;
			//得到筛选标签数量
			HashSet<String> hs = new HashSet<String>(queryList);
			int querylistnum = hs.size();
			//得到筛选标签数据
			String para = "";
			for (String query : queryList) {
				
				para = para + "'" + query + "',";
			
			}
			para = para.substring(0, para.length() - 1);
			
			String accessLogosql = "";
			String accessProjectsql = "";
			
			//定义规则三的增加数量在in条件中
			HashSet<String> bzLogo = new HashSet<String>();
			
			//定义规则四筛选SQL
			String accessLogoSQL = "";
			String accessProjectSQL = "";
			// 将目标业态处理，用于规则四的判断
			String[] accessLogoList1 = accessLogo.split("\\|");
			for(int i=0;i<accessLogoList1.length;i++){
				if(accessLogoList1[i].equals("10001")||accessLogoList1[i].equals("10002")||accessLogoList1[i].equals("10003")||accessLogoList1[i].equals("10004")){
					accessLogoList1[i]="";
				}
			}
			List<String> accessLogoList = new ArrayList<>();
			
			for (String string : accessLogoList1) {
				if(!string.equals("")){
					accessLogoList.add(string);
				}
			}
			
			//将规则四的业态条件补全
			//当目标业态与员工一致，且无其他
			if(accessLogoList.size()==1&&accessLogoList.contains(userLogo)){
				accessLogoSQL="( b.cust_logo like '%" + userLogo
						+ "%')";
			}
			//当目标业态与员工不一致，且无其他
//			if(accessLogoList.size()==1&&!accessLogoList.contains(userLogo)){
//				accessLogoSQL="( b.cust_logo not like '%" + userLogo
//						+ "%' and b.cust_logo like '%"+accessLogoList.get(0)+"%' )";
//				
//			}
//			//当目标业态与员工一致，含其他业态
//			if(accessLogoList.size()>1&&accessLogoList.contains(userId)){
//				accessLogoSQL+="( ";
//				for (String string : accessLogoList) {
//					accessLogoSQL+="  b.cust_logo like '%"
//							+ string+"%' and";
//				}
//				accessLogoSQL=accessLogoSQL.substring(accessLogoSQL.length()-3, accessLogoSQL.length());
//				accessLogoSQL+=")";
//				
//			}
//			//当目标业态与员工不一致，含其他业态
//			if(accessLogoList.size()>1&&!accessLogoList.contains(userId)){
//				accessLogoSQL+="(  b.cust_logo not like '%"+userLogo+"%' and (";
//				for (String string : accessLogoList) {
//					accessLogoSQL+="  b.cust_logo like '%"
//							+ string+"%' and";
//				}
//				accessLogoSQL=accessLogoSQL.substring(accessLogoSQL.length()-3, accessLogoSQL.length());
//				accessLogoSQL+="))";
//			}
			
			//将规则四的项目条件补全
			String[] accessProjectList1 = accessProject.split("\\|");
			List<String> accessProjectList = new ArrayList<>();
			Collections.addAll(accessProjectList, accessProjectList1);
			//将空字符串清除，因为其可能不存在
			for (String string : accessProjectList) {
				if ("".equals(string)) {
					tem_num1++;
				}
			}
			for (int i = 0; i < tem_num1; i++) {
				try {
					accessProjectList.remove(accessProjectList.indexOf(""));
				} catch (Exception e) {
				}
			}
			//讲规则四的项目条件补全
			//当目标项目与员工一致，且无其他
			if(accessProjectList.size()==1&&accessProjectList.contains(userProject)){
				accessProjectSQL=" ( b.project_id like '%"+userProject
						+"%')";
			}
			//当目标项目与员工一致，其他
			if(accessProjectList.size()==1&&!accessProjectList.contains(userProject)){
				accessProjectSQL=" ( b.project_id not like '%"+userProject
						+"%' and b.project_id like '%" + accessProjectList.get(0)
						+ "%')";
			}
			
			//当目标项目与员工不一致，且无其他
			if(accessProjectList.size()>1&&accessProjectList.contains(userProject)){
				
				accessProjectSQL+="( ";
				for (String string : accessProjectList) {
					accessProjectSQL+="  b.project_id like '%"
							+ string+"%' and";
				}
				accessProjectSQL=accessProjectSQL.substring(0, accessProjectSQL.length()-3);
				accessProjectSQL+=")";
				
			}
			//当目标项目与员工不一致，其他
			if(accessProjectList.size()>1&&!accessProjectList.contains(userProject)){
				
				accessProjectSQL+="(  b.project_id not like '%"+userProject+"%' and (";
				for (String string : accessProjectList) {
					accessProjectSQL+="  b.project_id like '%"
							+ string+"%' and";
				}
				accessProjectSQL=accessProjectSQL.substring(0, accessProjectSQL.length()-3);
				accessProjectSQL+="))";
				
			}
			
			//accessLogoSQL就是规则四的where条件,将where条件合并起来
			if(!accessProjectSQL.equals("")){
				accessLogoSQL=accessProjectSQL + (accessLogoSQL.equals("")?" ":" and ")+accessLogoSQL;
			}
			
			// or需要判断在添加
			//判断员工项目是否存在
			boolean link3 = !userProject.equals("");
			int usernum = 0;
			int usernum1 = 0;
			//同业态下的规则三
			if(accessLogoList.contains(project_Logo)){
			
			
			for(String string :queryList){
							string = string.substring(string.indexOf(":")+1, string.lastIndexOf(":"));
							if(!string.contains("|"))continue;
							bzLogo.add(" 'futureland_real_estate:"+string
									+ ":is_expired_protect_time_否',");
							usernum1++;
							if(string.endsWith(userProject)){
								usernum++;
							}
				}
			}
			
			int addnum = bzLogo.size();
			
			String in = "";
			
			if(usernum!=usernum1){
			for (String string : bzLogo) {
				in += string;
			}
			in = in.substring(0, in.length()-1);
			}else{
				addnum=0;
			}
			boolean addcount = addnum == 0;
			
			
			//用将空字符串的数据进行拼接sql
			for (int i = 0; i < accessLogoList.size(); i++) {
				accessLogosql += " cust_logo like '%" + accessLogoList.get(i) + "%' "
						+ (i == accessLogoList.size() - 1 ? "" : " and ");
			}
			for (int i = 0; i < accessProjectList.size(); i++) {
				accessProjectsql += " project_id like '%" + accessProjectList.get(i) + "%' "
						+ (i == accessProjectList.size() - 1 ? "" : " and ");
			}
			//最终判断目标与项条件是否存在
			boolean link4 = !accessLogosql.equals("");
			boolean link5 = !accessProjectsql.equals("");
			

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Calendar calendar = Calendar.getInstance();// 日历对象
			calendar.setTime(new Date());// 设置当前日期
			calendar.add(Calendar.MONTH, -1);// 月份减一
			String time = df.format(calendar.getTime()) + "";

			String gz4 = " and ("+accessLogoSQL+")";
			
//			+ (!addcount ? in : "")
			
			String sql = "select count(b.*) count from (" + " select superid from (" + " select superid FROM"
					+ " tag.user_label_info a WHERE" + " taginfo IN(" + para  + ") "
					+ (!addcount?" and superid not in (select superid from tag.user_label_info a where taginfo in ("+in+"))":"")
					+ " " // 判断是否为目标地产
					+ ")a  " + " group by superid having count(*)=" + (querylistnum )
					+ " )a inner join dw.cust_project_relation b on a.superid=b.superid"
					+ (link4 || link5 ? " where " : " ") + (link4 ? "(" + accessLogosql + ")" : " ")
					+ (link4 && link5 ? " and " : " ") + (link5 ? "(" + accessProjectsql + ")" : " ")
					+ (!accessLogoSQL.equals("") ? gz4 : "") 
					+ " and b.superid not in" + " ("
					+ " select cust_id from tag.user_access_log b" + " where udate>='" + time + "'  and status=1 "
					+ " and access_id='" + userId + "'" + " and access_logo='" + userLogo + "'"
					+ (link3 ? " and access_project='" + userProject + "'" : "") + " union "
					+ " select cust_id from tag.user_access_log b" + " where  udate>='" + time + "'  and status=1 "
					+ " and access_logo='" + userLogo + "'" + (link3 ? (" and access_project='" + userProject + "'") : "")
					+ " and cust_logo like '%" + userLogo + "%' group by cust_id having count(*)>=2)" ;
			
			String begin = "set optimizer = 1";
			conn.prepareStatement(begin).execute();
			long start = new Date().getTime();
			LogUtils.info(sql);
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			long end = new Date().getTime();
			LogUtils.info((end-start)+"ms 查找用户群数量KHAN执行时间");
			while(rs.next()){
				num = Long.valueOf(rs.getString("count"));
			}
		} finally{
			khan.close(stmt, rs);
			khan.close();
		}
		return num;
	}

}
