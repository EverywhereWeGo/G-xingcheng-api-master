package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.vo.UserChargingDayVo;

public interface UserChargingDayMapper {

	//api接口列表
    List<UserChargingDayVo> selectUserChargingDays(Map<String, Object> paramsMap);
    
    //总接口数
    int count();
}