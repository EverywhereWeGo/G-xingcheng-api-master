package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;


import com.bfd.api.manage.vo.ProChargeDay;

public interface ProjectChargingDayMapper {
	
	//api接口列表
    List<ProChargeDay> selectProChargeDay(Map<String, Object> paramsMap);
    
    //总接口数
    int count();
}