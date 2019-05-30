package com.bfd.api.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESUtils {

	private static final Log log = LogFactory.getLog(ESUtils.class);

	public static TransportClient client = null;

	static {
		init();
	}

	public static void init() {
		try {
			String clusterName = Constants.ES_CLUSTER;
			String ips = Constants.ES_IP;
			int port = Integer.parseInt(Constants.ES_PORT);

			Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
			client = TransportClient.builder().settings(settings).build();
			String[] ipArray = ips.split(",",-1);
			for (String ip : ipArray) {
				try {
					client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public static TransportClient getClient(){
		if(client==null){
			init();
		}
		return client;
	}
}
