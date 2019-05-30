package com.bfd.api.utils;

import org.apache.commons.lang.StringUtils;

public class Constants {
	
	public static String CHARSET="utf-8";
	
	public static String ZK_HOST;
	
	public static String REDIS_PROXY_PATH;
	public static String REDIS_BUSINESS_ID;
	
	public static String KHAN_USERNAME;
	public static String KHAN_PASSWORD;
	public static String KHAN_DRIVER;
	public static String KHAN_URL;

	public static String ES_CLUSTER;
	public static String ES_IP;
	public static String ES_PORT;
	public static String ES_LABEL;
	public static String HBASE_RELATION_GRAPH;
	public static String HBASE_USER;
	public static String HBASE_USER_COLUMN;
	public static String TOKEN_EXPIRE;
	
	public static String REDIS_ES_GROUPING_KEY="grouping";
	public static String REDIS_USER_DISREPEAT="disrepeat";
	public static String HBASE_USER_ACCESS_LOG="user_access_log:access_log";
	public static String HBASE_USER_ACCESS_LOGO="user_access_log:access_logo";
	public static String ACCESS_ON="on";
	public static String ACCESS_OFF="off";
	
	static{
		
		if(StringUtils.isEmpty(TOKEN_EXPIRE)){
			TOKEN_EXPIRE = ConfigUtils.getString("token.expire");
		}
		
		if(StringUtils.isEmpty(HBASE_USER_COLUMN)){
			HBASE_USER_COLUMN = ConfigUtils.getString("hbase.user.column");
		}
		
		if(StringUtils.isEmpty(HBASE_RELATION_GRAPH)){
			HBASE_RELATION_GRAPH = ConfigUtils.getString("hbase.relation.graph");
		}
		
		if(StringUtils.isEmpty(HBASE_USER)){
			HBASE_USER = ConfigUtils.getString("hbase.user");
		}
		
		if(StringUtils.isEmpty(ZK_HOST)){
			ZK_HOST = ConfigUtils.getString("zk.host");
		}
		
		if(StringUtils.isEmpty(REDIS_PROXY_PATH)){
			REDIS_PROXY_PATH = ConfigUtils.getString("ds.redis.proxyPath");
		}
		
		if(StringUtils.isEmpty(REDIS_BUSINESS_ID)){
			REDIS_BUSINESS_ID = ConfigUtils.getString("ds.redis.businessid");
		}
		
		if(StringUtils.isEmpty(ES_CLUSTER)){
			ES_CLUSTER = ConfigUtils.getString("es.cluster.name");
		}
		
		if(StringUtils.isEmpty(ES_IP)){
			ES_IP = ConfigUtils.getString("es.ip");
		}
		
		if(StringUtils.isEmpty(ES_PORT)){
			ES_PORT = ConfigUtils.getString("es.port");
		}
		
		if(StringUtils.isEmpty(KHAN_DRIVER)){
			KHAN_DRIVER = ConfigUtils.getString("khan.driver");
		}
		
		if(StringUtils.isEmpty(KHAN_PASSWORD)){
			KHAN_PASSWORD = ConfigUtils.getString("khan.password");
		}
		
		if(StringUtils.isEmpty(KHAN_URL)){
			KHAN_URL = ConfigUtils.getString("khan.url");
		}
		
		if(StringUtils.isEmpty(KHAN_USERNAME)){
			KHAN_USERNAME = ConfigUtils.getString("khan.username");
		}
		
		if(StringUtils.isEmpty(ES_LABEL)){
			ES_LABEL = ConfigUtils.getString("es.index");
		}
	}
}
