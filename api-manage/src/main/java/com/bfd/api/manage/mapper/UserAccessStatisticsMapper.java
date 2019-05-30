package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.domain.UserAccessStatistics;
import com.bfd.api.manage.vo.UserAccess;
import com.bfd.api.manage.vo.UserAccessCount;

public interface UserAccessStatisticsMapper {

    //用户访问接口
    List<UserAccess> selectUserAccessStatisticByIds(Map<String, Object> paramsMap);
    
    //用户访问接口统计
    List<UserAccessCount> selectUserAccessCount(Map<String, Object> paramsMap);
}