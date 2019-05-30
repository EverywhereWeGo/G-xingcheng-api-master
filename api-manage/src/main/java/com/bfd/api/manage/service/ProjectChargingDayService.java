package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.vo.ProChargeDay;

public interface ProjectChargingDayService {

	PageInfo<ProChargeDay> getProChargeDays(Integer currentPage,
			Integer pageSize, String logo, String project, 
			Long apiId, Integer chargeType, String accessMonth
			);
		
}
