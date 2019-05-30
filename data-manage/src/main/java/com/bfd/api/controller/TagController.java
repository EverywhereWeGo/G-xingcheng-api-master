package com.bfd.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bfd.api.common.BaseController;
import com.bfd.api.common.ReturnJson;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.service.MicroProfileService;
import com.bfd.api.service.TagService;
import com.bfd.api.utils.Encrypt;
import com.bfd.api.utils.LogUtils;
import com.bfd.api.utils.isMobileMode;
import com.bfd.api.vo.TokenVo;
import com.bfd.api.vo.UserInfo;

@RestController
@RequestMapping(value = "/tag",produces = { "application/json;charset=UTF-8" })
public class TagController extends BaseController {
	
	@Autowired
	private TagService tagService;

	@Autowired
	private MicroProfileService microProfileService;
	/**
	 * 查询微观画像
	 * @param queryType
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/graph/getGraph")
	public void getGraph(@RequestParam(value = "queryType", required = true) String queryType,
			@RequestParam(value = "key", required = true) String key) {
		boolean result = true;
		try{
			long start = new Date().getTime();
			String keyNew = key; 
			while(keyNew.startsWith("0")){
				keyNew = keyNew.substring(1, keyNew.length());
			}
			if("mobile".equals(queryType)){
				result=isMobileMode.isMobileNO(keyNew);
				if(result==false){
					throw new Exception();
				}
			}
			String graph=microProfileService.getGraph(queryType, keyNew);
			long end = new Date().getTime();
			LogUtils.info((end-start)+"ms queryType="+queryType+" key="+key);
			this.writeJson(graph);
		}catch(Exception e){
			LogUtils.error(e);
			this.writeJson(ReturnJson.getFailedJson(result?"": "mobile is incorrect"));
		}
	}
	
	/**
	 * 用户群覆盖人数查询接口
	 * @return
	 */
	@RequestMapping(value = "/group/getUserNum")
	public void getUserNum(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "userLogo", required = true) String userLogo,
			@RequestParam(value = "userProject", required = false,defaultValue = "") String userProject,
			@RequestParam(value = "accessLogo", required = true) String accessLogo,
			@RequestParam(value = "accessProject", required = false, defaultValue = "") String accessProject,
			@RequestParam(value = "grouping", required = true) String grouping){
		try {
			 long start = new Date().getTime();
			 long num = tagService.getUserNum(userId, userLogo, userProject,accessLogo,accessProject, grouping);
			 long end = new Date().getTime();
			 LogUtils.info((end-start)+"ms userId="+userId+";userLogo="+userLogo+";userProject="+userProject+";accessLogo="+accessLogo+";accessProject="+accessProject
						+";grouping="+grouping);
			 this.writeJson(ReturnJson.getSuccessJson(num));
		} catch (Exception e) {
			 LogUtils.error(e);
			 this.writeJson(ReturnJson.getFailedJson());
		}
	}
	
	/**
	 * 用户群查询接口
	 * @return
	 */
	@RequestMapping(value = "/group/getUserList")
	public void getUserList(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "userLogo", required = true) String userLogo,
			@RequestParam(value = "userProject", required = false, defaultValue = "") String userProject,
			@RequestParam(value = "accessLogo", required = true) String accessLogo,
			@RequestParam(value = "accessProject", required = false, defaultValue = "") String accessProject,
			@RequestParam(value = "grouping", required = true) String grouping,
			@RequestParam(value = "currentPage", required = false , defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false , defaultValue = "2000") Integer pageSize,
			@RequestParam(value = "access_token", required = true ) String access_token
			){
		try {
			long start = new Date().getTime();
			access_token = access_token.replace(" ", "+");
			String token = Encrypt.decrypt(access_token);
			TokenVo vo = JSONObject.parseObject(token, TokenVo.class);
			String appCode =  vo.getAppCode();
			PageInfo<UserInfo> list = tagService.getUserList(userId, userLogo, userProject,accessLogo, accessProject, grouping,appCode, currentPage, pageSize);
			long end = new Date().getTime();
			LogUtils.info((end-start)+"ms userId="+userId+";userLogo="+userLogo+";userProject="+userProject+";accessLogo="+accessLogo+";accessProject="+accessProject
					+";grouping="+grouping+";appCode="+appCode);
			this.writeResult(ReturnJson.getSuccessJson(list));
		} catch (Exception e) {
			LogUtils.error(e);
			this.writeJson(ReturnJson.getFailedJson());
		}
	}
	
	/**
	 * 短信平台对接查询数量接口
	 * @return
	 */
	@RequestMapping(value = "/group/getCommonUserNum")
	public void getCommonUserNum(
			@RequestParam(value = "json", required = true) String json,
			@RequestParam(value = "access_token", required = true ) String access_token
			){
		try {
			json = json.replace("[", "{").replace("]", "}");
			long start = new Date().getTime();
			access_token = access_token.replace(" ", "+");
			String token = Encrypt.decrypt(access_token);
			TokenVo vo = JSONObject.parseObject(token, TokenVo.class);
			Long appId =  vo.getAppId();
			Long num = tagService.getUserNumCommon(appId,json);
			long end = new Date().getTime();
			LogUtils.info((end-start)+"ms "+appId);
			this.writeJson(ReturnJson.getSuccessJson(num));
		} catch (Exception e) {
			LogUtils.error(e);
			this.writeJson(ReturnJson.getFailedJson());
		}
	}


	/**
	 * 短信平台对接查询接口
	 * @return
	 */
	@RequestMapping(value = "/group/getCommonUserList")
	public void getCommonUserList(
			@RequestParam(value = "json", required = true) String json,
			@RequestParam(value = "currentPage", required = false , defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false , defaultValue = "10") Integer pageSize,
			@RequestParam(value = "access_token", required = true ) String access_token
			){
		try {
			json = json.replace("[", "{").replace("]", "}");
			long start = new Date().getTime();
			access_token = access_token.replace(" ", "+");
			String token = Encrypt.decrypt(access_token);
			TokenVo vo = JSONObject.parseObject(token, TokenVo.class);
			Long appId =  vo.getAppId();
			Long num = tagService.getUserNumCommon(appId, json);
			PageInfo<UserInfo> list = tagService.getUserListCommon(appId,json,currentPage,pageSize,num);
			long end = new Date().getTime();
			this.writeResult(ReturnJson.getSuccessJson(list));
			LogUtils.info((end-start)+"ms end end end"+appId);
		} catch (Exception e) {
			LogUtils.error(e);
			this.writeJson(ReturnJson.getFailedJson());
		}
	}
	
	
}
