package com.bfd.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bfd.api.service.KafkaInService;
import com.bfd.api.utils.KafkaProducer;

@Service("kafkaInService")
@Transactional
public class KafkaInServiceImpl implements KafkaInService {
	
	@Override
	public boolean setKafka(String topic, String jsonArray) {
		boolean result = KafkaProducer.send(topic, jsonArray);
		return result;
	}
	
	@Override
	public boolean setKafka1() {
					    	for(int j=1;j<=10;j++){
			    		String sql = "[";
			    		for(int i =0 ;i<1000;i++){
			    		sql=sql+"{\"ap_mac_address\":\"one"+j
			    				+ "\","
			    				+ "\"Vendor_ID\":\"1\",\"mu_mac_address\":\"xmb"+i
			    				+ "\""
			    				+ ","
			    				+ "\"Radio_Type\":\"3.1\","
			    				+ "\"Channel\":\"1\","
			    				+ "\"Is_Associated\":\"1\","
			    				+ "\"AssociatedAP_Mac\":\"1\","
			    				+ "\"MU_Type\":\"1\","
			    				+ "\"RSSI\":\"1\","
			    				+ "\"Noise_Floor\":\"1\","
			    				+ "\"Age\":\"1\","
			    				+ "\"MU_IPv4\":\"1\","
			    				+ "\"Reserved\":\"1\""
			    				+ "},";
			    		}
			    		sql = sql.substring(0, sql.length()-1);
			    		sql = sql+"]";
			    		setKafka2("xmbtest3",sql);
			    		}
		return true;
	}
	public static boolean setKafka2(String topic, String jsonArray) {
		boolean result = KafkaProducer.send(topic, jsonArray);
		return result;
	}
}





