package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.service.UserChargingDayService;
import com.bfd.api.manage.vo.UserChargingDayVo;
import com.bfd.api.utils.ReturnJsonUtils;



@RestController
@RequestMapping(value = "/user",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class UserChargingDayController extends BaseController {

	@Autowired
	private UserChargingDayService userChargingDayService;
	
	@RequestMapping(value = "/dayCount")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15")  Integer pageSize,
			@RequestParam(value = "userId", required = true)  String userId,
			@RequestParam(value = "userLogo", required = true)  String userLogo,
			@RequestParam(value = "userProject", required = true)  String userProject,
			@RequestParam(value = "apiId", required = true)  Long apiId,
			@RequestParam(value = "chargeType", required = true)  Integer chargeType,
			@RequestParam(value = "accessMonth", required = true)  String  accessMonth){
		
		try {
			PageInfo<UserChargingDayVo> userChargingDays = userChargingDayService.getUserChargingDays(currentPage, pageSize, userId, userLogo, userProject, apiId, chargeType, accessMonth);
			return ReturnJsonUtils.getSuccessJson("1", userChargingDays);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
}
}
