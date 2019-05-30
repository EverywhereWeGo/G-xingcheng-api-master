package com.bfd.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bfd.api.service.KafkaInService;
import com.bfd.api.utils.KafkaProducer;

@Service("kafkaInService")
@Transactional
public class KafkaInServiceImpl implements KafkaInService {
	

	@Override
	public boolean setKafka1() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean setKafka(String topic, String jsonArray, String type) {
		boolean result;
		result = KafkaProducer.send(topic, jsonArray);
		
		return result;
	}


}





