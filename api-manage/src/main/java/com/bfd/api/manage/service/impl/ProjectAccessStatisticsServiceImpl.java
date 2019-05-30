package com.bfd.api.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ProjectAccessStatistics;
import com.bfd.api.manage.mapper.ProjectAccessStatisticsMapper;
import com.bfd.api.manage.service.ProjectAccessStatisticsService;
import com.bfd.api.manage.vo.ProAccessCount;
import com.bfd.api.manage.vo.ProjectAccess;


@Service("projectAccessStatisticsService")
@Transactional
public class ProjectAccessStatisticsServiceImpl implements ProjectAccessStatisticsService {
	
	@Autowired
	private ProjectAccessStatisticsMapper projectAccessStatisticsMapper;

	@Override
	public PageInfo<ProjectAccess> getAllProjectAccessStatistics(Integer currentPage, Integer pageSize,String logo,String project,Long apiId) {
		PageInfo<ProjectAccess> result = new PageInfo<ProjectAccess>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("logo", logo);
		paramsMap.put("project", project);
		paramsMap.put("apiId", apiId);
		List<ProjectAccess> list = projectAccessStatisticsMapper.selectAllProjectAccessStatistics(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}

	@Override
	public PageInfo<ProAccessCount> getAllProAccessCounts(Integer currentPage, Integer pageSize) {
		PageInfo<ProAccessCount> result = new PageInfo<ProAccessCount>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<ProAccessCount> list = projectAccessStatisticsMapper.selectProAccessCount(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}
}
