package com.bfd.api.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.UserAccessStatistics;
import com.bfd.api.manage.mapper.UserAccessStatisticsMapper;
import com.bfd.api.manage.service.UserAccessStatisticsService;
import com.bfd.api.manage.vo.UserAccess;
import com.bfd.api.manage.vo.UserAccessCount;

@Service("userAccessStatisticsService")
@Transactional
public class UserAccessStatisticsServiceImpl implements UserAccessStatisticsService {

	@Autowired
	private UserAccessStatisticsMapper userAccessStatisticsMapper;
	
	@Override
	public PageInfo<UserAccessCount> getUserAccessCount(Integer currentPage, Integer pageSize) {
		PageInfo<UserAccessCount> result = new PageInfo<UserAccessCount>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<UserAccessCount> list = userAccessStatisticsMapper.selectUserAccessCount(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}

	@Override
	public PageInfo<UserAccess> getUserAccessStatistics(Integer currentPage, Integer pageSize, String userId,
			String logo, String project, Long apiId, Long appId) {
		PageInfo<UserAccess> result = new PageInfo<UserAccess>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("userId", userId);
		paramsMap.put("logo", logo);
		paramsMap.put("project", project);
		paramsMap.put("apiId", apiId);
		paramsMap.put("appId", appId);
		List<UserAccess> list = userAccessStatisticsMapper.selectUserAccessStatisticByIds(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}

}
