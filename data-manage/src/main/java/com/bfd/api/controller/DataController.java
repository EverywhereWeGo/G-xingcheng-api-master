package com.bfd.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bfd.api.common.BaseController;
import com.bfd.api.domain.App;
import com.bfd.api.service.AppService;

/**
 * 此类只提供数据服务通用接口
 * @author BFD_523
 *
 */
@RestController
@RequestMapping(value = "/data",produces = { "application/json;charset=UTF-8" })
public class DataController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Autowired
	private AppService appService;

	/**
	 * 获取token
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getToken")
	public void getToken(@RequestParam(value = "appCode", required = true) String appCode,
			@RequestParam(value = "appSecret", required = true) String appSecret) {
		
		Map<String, Object> error = new HashMap<String, Object>();
		try {
			App app = appService.getAppByCode(appCode,appSecret);
			if (app == null || !StringUtils.equals(appSecret, app.getAppSecret())) {
				error.put("errcode", 3201);
				error.put("errmsg", "invalid appId or accessKey");
				this.writeResult(error);
			}

			Map<String, Object> succcess = appService.getToken(app);
			this.writeJson(succcess);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error",e);
			error.put("errcode", 3500);
			error.put("errmsg", "exception");
			this.writeJson(error);
		}
		
	}

}
