package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.vo.ProAccessCount;
import com.bfd.api.manage.vo.ProjectAccess;

public interface ProjectAccessStatisticsMapper {

    //项目访问接口
    List<ProjectAccess> selectAllProjectAccessStatistics(Map<String, Object> paramsMap);
    
    //总接口数
    int count();
    
    //项目访问接口总数
    List<ProAccessCount> selectProAccessCount(Map<String, Object> paramsMap);
    
    
}