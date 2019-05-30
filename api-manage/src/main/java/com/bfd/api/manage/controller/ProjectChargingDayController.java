package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.ProjectChargingDay;
import com.bfd.api.manage.service.ProjectChargingDayService;
import com.bfd.api.manage.vo.ProChargeDay;
import com.bfd.api.utils.ReturnJsonUtils;



@RestController
@RequestMapping(value = "/project",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class ProjectChargingDayController extends BaseController {

	@Autowired
	private ProjectChargingDayService projectChargingDayService;
	
	@RequestMapping(value = "/dayCount")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15")  Integer pageSize,
			@RequestParam(value = "logo", required = true)  String logo,
			@RequestParam(value = "project", required = true)  String project,
			@RequestParam(value = "apiId", required = true)  Long apiId,
			@RequestParam(value = "chargeType", required = true)  Integer chargeType,
			@RequestParam(value = "accessMonth", required = true)  String accessMonth){
		
		try {
			PageInfo<ProChargeDay> proChargeDays = projectChargingDayService.getProChargeDays(currentPage, pageSize, logo, project, apiId, chargeType, accessMonth);
			return ReturnJsonUtils.getSuccessJson("1", proChargeDays);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
}
