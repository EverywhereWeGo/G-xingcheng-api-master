package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.domain.UserChargingMonth;
import com.bfd.api.manage.vo.UserChargeMonth;
import com.bfd.api.manage.vo.UserChargingMonthVo;

public interface UserChargingMonthMapper {

	//条件查询
    List<UserChargingMonthVo> selectUserChargingMonthById(Map<String, Object> paramsMap);
    
    //总接口数
    int count();
    
    //用户访问月统计
    List<UserChargeMonth>  selectUserChargeMonth(Map<String, Object> paramsMap);
}