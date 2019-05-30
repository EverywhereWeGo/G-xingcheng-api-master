package com.bfd.api.utils;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseUtils {

	private static final Logger logger = LoggerFactory.getLogger(HBaseUtils.class);
	private static Connection hbaseConn;
	
	private HBaseUtils(){}
	
	static{
		init();
	}
	
	public static Connection geConnection(){
		return hbaseConn;
	}
	
	public static void init(){
		String os = System.getProperty("os.name");
		if(os.toLowerCase().indexOf("windows") != -1){
			System.setProperty("hadoop.home.dir", "E:\\server\\hadoop-2.6.0");
		}
		
		if(hbaseConn != null){
			try {
				hbaseConn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			hbaseConn = null;
		}
		
		logger.info("HBASE_ZK_HOST=" + Constants.ZK_HOST);
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", Constants.ZK_HOST);
		conf.set("zookeeper.znode.parent", "/hbase-unsecure");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.setInt("hbase.rpc.timeout",20000);
		conf.setInt("hbase.client.operation.timeout",30000);
		conf.setInt("hbase.client.scanner.timeout.period",200000);
		try{
			UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hbase");
			User user = User.create(ugi);
			hbaseConn = ConnectionFactory.createConnection(conf, user);
			logger.info("HBaseInif --> 初始化HBase连接成功……");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
