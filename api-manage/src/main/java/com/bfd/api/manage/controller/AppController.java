package com.bfd.api.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.App;
import com.bfd.api.manage.service.AppService;
import com.bfd.api.utils.LogType;
import com.bfd.api.utils.LogUtil;
import com.bfd.api.utils.ReturnJsonUtils;
import com.bfd.api.utils.log.OpBehaviorType;

@RestController
@RequestMapping(value = "/app",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class AppController extends BaseController{
	
	@Autowired
	public AppService appService;
	
	@RequestMapping(value = "/get")  
	public Result<Object> get(@RequestParam(value = "id", required = true) Long id){
		
		try{
			App app = appService.get(id);
			if(app != null)
			     return ReturnJsonUtils.getSuccessJson("000000", app);
			else
				return ReturnJsonUtils.getFailedJson("000000", "wc");
		}catch(Exception e){
			LogUtil.getLogger(LogType.Run).error("id="+id+ "查询应用信息失败",e);
			this.setOpErrorLog(OpBehaviorType.select, e);
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("111111", "查询应用信息失败");
		}
	}
}
