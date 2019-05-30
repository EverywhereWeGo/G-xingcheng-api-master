package com.bfd.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.Result;
import com.bfd.api.service.KafkaInService;
import com.bfd.api.utils.ReturnJsonUtils;

/**
 * 此类只提供kafka接数
 * @author BFD_523
 *
 */
@RestController
@RequestMapping(value = "/data",produces = { "application/json;charset=UTF-8" })
public class DataController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Autowired
	private KafkaInService kafkaInService;

	
	@RequestMapping(value = "/import")
	public Result<Object> setKafka_WYGC(@RequestParam(value = "supplier", required = true) String supplier,
			@RequestParam(value = "tableName", required = true) String tableName,
			@RequestParam(value = "json", required = true) String json,
			@RequestParam(value = "type", required = false , defaultValue = "") String type){
		boolean status = false;
//		String topic_name = (supplier+"_"+tablename+"_"+vendor_id).toLowerCase();
		String topic_name = (supplier+"_"+tableName).toLowerCase();
		try {
			status=kafkaInService.setKafka(topic_name, json,type);
			if(status==true){
				return ReturnJsonUtils.getSuccessJson("1", "");
			}
			logger.info("data", supplier+"_"+tableName);
//			logger.info("data", supplier+"_"+tablename+"_"+vendor_id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error",e);
		}
		return ReturnJsonUtils.getFailedJson("0", "failed");
	}
	
}

