package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.vo.UserAccess;
import com.bfd.api.manage.vo.UserAccessCount;

public interface UserAccessStatisticsService {
    
	//条件查询
 	PageInfo<UserAccess> getUserAccessStatistics(Integer currentPage,
			Integer pageSize,String userId, String logo, String project,
			Long apiId, Long appId);
 	
 	//统计
 	PageInfo<UserAccessCount> getUserAccessCount(Integer currentPage,
			Integer pageSize);
}
