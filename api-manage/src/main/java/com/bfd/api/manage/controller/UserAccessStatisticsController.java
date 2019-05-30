package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.UserAccessStatistics;
import com.bfd.api.manage.service.UserAccessStatisticsService;
import com.bfd.api.manage.vo.UserAccess;
import com.bfd.api.manage.vo.UserAccessCount;
import com.bfd.api.utils.ReturnJsonUtils;

@RestController
@RequestMapping(value = "/user",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class UserAccessStatisticsController extends BaseController{

	@Autowired
	public UserAccessStatisticsService userAccessStatisticsService;
	
	@RequestMapping(value = "/getCount")  
	public Result<Object> getCount(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize){
		try {
			PageInfo<UserAccessCount> userAccessCounts = userAccessStatisticsService.getUserAccessCount(currentPage, pageSize);
			return ReturnJsonUtils.getSuccessJson("1", userAccessCounts);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
	
	@RequestMapping(value = "/getAll")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "logo", required = true) String logo,
			@RequestParam(value = "project", required = true) String project,
			@RequestParam(value = "apiId", required = true) Long apiId,
			@RequestParam(value = "appId", required = true) Long appId){
		try {
			PageInfo<UserAccess> userAccessStatistics = userAccessStatisticsService.getUserAccessStatistics(currentPage, pageSize, userId, logo, project, apiId, appId);
			return ReturnJsonUtils.getSuccessJson("1", userAccessStatistics);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
}
