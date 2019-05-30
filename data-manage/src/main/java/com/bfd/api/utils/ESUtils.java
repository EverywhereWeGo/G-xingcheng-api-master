package com.bfd.api.utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.bfd.api.vo.UserInfo_SMS;

public class ESUtils {

	private TransportClient transportClient;
    private static ESUtils clientInstance;	
	
	
	
    private ESUtils() throws Exception{
		String clusterName = Constants.ES_CLUSTER;
		String ips = Constants.ES_IP;
		int port = Integer.parseInt(Constants.ES_PORT);
	     // 配置信息
		Settings settings = Settings.settingsBuilder()
                .put("cluster.name",clusterName)//我的集群名
                .put("client.transport.sniff",true)  
                .build();
        transportClient = TransportClient.builder().settings(settings).build()
        		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips.split(",")[0]), port))
        		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips.split(",")[1]), port))
        		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips.split(",")[2]), port))
        		;

	}
	
	
	public static ESUtils getInstance() throws Exception{
        if(clientInstance==null){
            clientInstance = new ESUtils();
        }
        return clientInstance;
    }

    public TransportClient getTransportClient() {
        return transportClient;
    }
	
	
    public static List<UserInfo_SMS> SMS_FIND(String index,String type,String dsl,String mb) throws Exception {
        TransportClient client = ESUtils.getInstance().getTransportClient();
        SearchResponse searchResponse = client.prepareSearch(index).setTypes(type).setSource(dsl).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<UserInfo_SMS> list = new ArrayList<UserInfo_SMS>();
        //pageNo,pageSize
        for (SearchHit searchHit : hits) {
        	UserInfo_SMS sms = new UserInfo_SMS();
        	sms.setMobile((String) searchHit.getSource().get(mb));
        	list.add(sms);
        }
        System.out.println("查询到记录数:" + hits.getTotalHits());
		return list;
    }
	    
}

