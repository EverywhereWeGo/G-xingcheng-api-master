package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.service.ProjectChargingMonthService;
import com.bfd.api.manage.vo.ProChargeMonth;
import com.bfd.api.manage.vo.ProjectChargingMonthVo;
import com.bfd.api.utils.ReturnJsonUtils;



@RestController
@RequestMapping(value = "/project",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class ProjectChargingMonthController extends BaseController {

	@Autowired
	private ProjectChargingMonthService projectChargingMonthService;
	
	@RequestMapping(value = "/monthCount")  
	public Result<Object> getCount(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15")  Integer pageSize){
		
		try {
			PageInfo<ProChargeMonth> proChargeMonths = projectChargingMonthService.getProChargeMonths(currentPage, pageSize);
			return ReturnJsonUtils.getSuccessJson("1", proChargeMonths);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
   }
	
	@RequestMapping(value = "/month")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15")  Integer pageSize,
			@RequestParam(value = "logo", required = true)  String logo,
			@RequestParam(value = "project", required = true)  String project,
			@RequestParam(value = "apiId", required = true)  Long apiId,
			@RequestParam(value = "chargeType", required = true)  Integer chargeType){
		
		try {
			PageInfo<ProjectChargingMonthVo> projectChargingMonths = projectChargingMonthService.getProjectChargingMonths(currentPage, pageSize, logo, project, apiId, chargeType);
			return ReturnJsonUtils.getSuccessJson("1", projectChargingMonths);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
   }
}
