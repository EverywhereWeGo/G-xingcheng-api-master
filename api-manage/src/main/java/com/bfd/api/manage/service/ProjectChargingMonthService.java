package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ProjectChargingMonth;
import com.bfd.api.manage.vo.ProChargeMonth;
import com.bfd.api.manage.vo.ProjectChargingMonthVo;

public interface ProjectChargingMonthService {

	
	//项目访问接口月统计
	PageInfo<ProChargeMonth> getProChargeMonths(Integer currentPage,
			Integer pageSize);
	
	//条件查询
	PageInfo<ProjectChargingMonthVo> getProjectChargingMonths(Integer currentPage,
			Integer pageSize, String logo, String project, Long apiId, Integer chargeType);
}
