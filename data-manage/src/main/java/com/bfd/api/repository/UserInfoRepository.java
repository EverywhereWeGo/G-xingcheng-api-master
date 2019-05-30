package com.bfd.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import com.bfd.api.utils.Constants;
import com.google.common.collect.Maps;

public class UserInfoRepository extends HbaseRepository {
	
	/**
	 * 根据手机号批量查询
	 * @param superids
	 * @return
	 */
	public static List<Map<String, Object>> bulkGet(List<String> mobiles) throws Exception {
		Table table = null;
		try {
			table = getTable(Constants.HBASE_USER);
			if(table != null) {
				List<Get> list = new ArrayList<Get>();
				for(String id:mobiles){
					String key = getRowkey(id);
					Get get = new Get(key.getBytes()); //根据主键查询
					list.add(get);
				}
				
				Result[] rs = table.get(list);
				List<Map<String,Object>> userlist = new ArrayList<Map<String,Object>>();
				if(rs != null && rs.length>0) {
					for(Result r:rs){
						Map<String, Object> map = Maps.newHashMap();
						for (KeyValue keyValue : r.raw()) {
							String key = new String(keyValue.getQualifier());
							if(StringUtils.contains(Constants.HBASE_USER_COLUMN,key)){
								map.put(key, new String(keyValue.getValue()));
							}
						}
						if(map!=null && map.size()>0){
							userlist.add(map);
						}
					}
					return userlist;
				}
			}
		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
