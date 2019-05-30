package com.bfd.api.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.ProjectAccessStatistics;
import com.bfd.api.manage.service.ProjectAccessStatisticsService;
import com.bfd.api.manage.vo.ProAccessCount;
import com.bfd.api.manage.vo.ProjectAccess;
import com.bfd.api.utils.ReturnJsonUtils;

@RestController
@RequestMapping(value = "/project",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class ProjectAccessStatisticsController extends BaseController{

	@Autowired
	public ProjectAccessStatisticsService projectAccessStatisticsService;
	
	@RequestMapping(value = "/getCount")  
	public Result<Object> getProAccessCount(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize){
		try {
			PageInfo<ProAccessCount> proAccessCounts = projectAccessStatisticsService.getAllProAccessCounts(currentPage, pageSize);
			return ReturnJsonUtils.getSuccessJson("1", proAccessCounts);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
	
	@RequestMapping(value = "/byLogoProId")  
	public Result<Object> getProAccess(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize,
			@RequestParam(value = "logo", required = true) String  logo,
			@RequestParam(value = "project", required = true) String project,
			@RequestParam(value = "apiId", required = true) Long apiId){
		try {
			PageInfo<ProjectAccess> projectAccessStatistics = projectAccessStatisticsService.getAllProjectAccessStatistics(currentPage, pageSize, logo, project, apiId);
			return ReturnJsonUtils.getSuccessJson("1", projectAccessStatistics);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
}
