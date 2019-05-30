package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.vo.ProAccessCount;
import com.bfd.api.manage.vo.ProjectAccess;

public interface ProjectAccessStatisticsService {
     
 	PageInfo<ProjectAccess> getAllProjectAccessStatistics(Integer currentPage,
			Integer pageSize,String logo,String project,Long apiId);
 	
 	PageInfo<ProAccessCount> getAllProAccessCounts(Integer currentPage,
			Integer pageSize);
 	
}
