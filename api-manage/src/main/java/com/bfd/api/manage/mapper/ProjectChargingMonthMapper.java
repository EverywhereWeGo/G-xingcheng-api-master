package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.domain.ProjectChargingMonth;
import com.bfd.api.manage.vo.ProChargeMonth;
import com.bfd.api.manage.vo.ProjectChargingMonthVo;

public interface ProjectChargingMonthMapper {

    //项目访问接口月统计
	List<ProChargeMonth> selectProAccessMonth(Map<String, Object> paramsMap);
	
	//条件查询
	List<ProjectChargingMonthVo> selectProAccessMonthByLogo(Map<String, Object> paramsMap);
}