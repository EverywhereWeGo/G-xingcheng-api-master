package com.bfd.api.utils;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {

	private static Producer<Integer, String> producer;

	static{
		init();
	}
	
	private static void init() {
		try{
			Properties props = new Properties();
			props.put("metadata.broker.list", ConfigUtil.getInstance().get("kafka.broker.list"));
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			ProducerConfig config = new ProducerConfig(props);
			LogUtil.getLogger(LogType.Run).info( "brokerList=" + config.brokerList());
			LogUtil.getLogger(LogType.Run).info( "props=" + config.props().toString());
			producer = new Producer<Integer, String>(config);
		}catch (Exception ex) {
			LogUtil.getLogger(LogType.Run).error( "初始化KAFKA异常", ex);
		}
	}

	public static boolean send(String topic,String msg) {
		boolean result = false;
		LogUtil.getLogger(LogType.Run).info( "json=" + msg);
		try{
			producer.send(new KeyedMessage<Integer, String>(topic, msg));
			result = true;
		}catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error( "向KAFKA发送消息异常", e);
			try{
				producer.close();
			}catch (Exception ex) {
			}
			producer = null;
			init();
		}
		return result;
	}
	
	
}
