package com.bfd.api.repository;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bfd.api.utils.HBaseUtils;
import com.google.common.collect.Maps;

public class HbaseRepository {

	private static final Logger logger = LoggerFactory.getLogger(HbaseRepository.class);
	
	public synchronized static Table getTable(String tableName) {
		try {
			TableName tablename = TableName.valueOf(tableName);
			Connection coon = HBaseUtils.geConnection();
			if (coon == null) {
				HBaseUtils.init();
			}
			return coon.getTable(tablename);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询hbse表:"+tableName+"异常:",e);
			HBaseUtils.init();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static Map<String,Object> findById(String rowkey, String tableName) throws IOException{
		Table table = null;
		try {
			table = getTable(tableName);
			if(table != null) {
				Get get = new Get(rowkey.getBytes()); //根据主键查询
				Result r = table.get(get);
				if(r != null) {
					Map<String, Object> map = Maps.newHashMap();
					for (KeyValue keyValue : r.raw()) {
						String key = new String(keyValue.getQualifier());
						map.put(key, keyValue.getValue());
					}
                    return map;
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
	
	/**
	 * 生成hbase rowkey
	 * @param pks
	 * @return
	 */
	public static String getRowkey(String pks){
		return DigestUtils.md5Hex(pks).substring(0,2)+":"+pks;
	}
	
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("callke"));
		System.out.println(DigestUtils.md5Hex("callke20170622"));
		
		String ss = getRowkey("global:superid:d00014ce-ece9-4636-8901-1b08e5c173a2");
		System.out.println(ss);
	}
	
}
