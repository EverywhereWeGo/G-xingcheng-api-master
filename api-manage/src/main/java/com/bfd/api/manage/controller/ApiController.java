package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.Api;
import com.bfd.api.manage.service.ApiService;
import com.bfd.api.utils.ReturnJsonUtils;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/api",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class ApiController extends BaseController{
	
	@Autowired
	public ApiService apiService;
	
	@RequestMapping(value = "/getApi")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize,
			@RequestParam(value = "apiType", required = false, defaultValue = "-1") Integer apiType,
			@RequestParam(value = "apiName", required = false, defaultValue = "") String apiName,
			@RequestParam(value = "apiId", required = false, defaultValue = "-1") Long apiId){
		try {
			PageInfo<Api> apis = apiService.getApis(currentPage, pageSize, apiType, apiName, apiId);
			return ReturnJsonUtils.getSuccessJson("1", apis);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
	
	@RequestMapping(value = "/insertApi")  
	public Result<Object> insert(
			@RequestParam(value = "json", required = true) String  jsonstr){
		
		try {
			JSONObject json = JSONObject.fromObject(jsonstr);
			String apiName = json.getString("apiName");
			Integer apiType = json.getInt("apiType");
			Integer chargeType = json.getInt("chargeType");
			String url = json.getString("url");
			String descr = json.getString("descr");
			Integer status = json.getInt("status");
			apiService.insertApi(apiName, apiType, chargeType, url, descr, status);
			return ReturnJsonUtils.getSuccessJson("1", "");
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
	@RequestMapping(value = "/getApiByUrl")  
	public String getApiByUrl(@RequestParam(value = "url", required = true) String  url){
		Api result = new Api();
		try{
			result=apiService.getApiByUrl(url);
		}catch(Exception e){
			e.printStackTrace();
		}
		return JSONObject.fromObject(result).toString();
	}
	
}
