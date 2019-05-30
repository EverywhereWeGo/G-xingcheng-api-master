package com.bfd.api.manage.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ProjectChargingDay;
import com.bfd.api.manage.domain.ProjectChargingMonth;
import com.bfd.api.manage.mapper.ProjectChargingDayMapper;
import com.bfd.api.manage.service.ProjectChargingDayService;
import com.bfd.api.manage.vo.ProChargeDay;


@Service("projectChargingDayService")
@Transactional
public class ProjectChargingDayServiceImpl implements ProjectChargingDayService {

	@Autowired
	private ProjectChargingDayMapper projectChargingDayMapper;

	@Override
	public PageInfo<ProChargeDay> getProChargeDays(Integer currentPage, Integer pageSize, String logo, String project,
		Long apiId, Integer chargeType, String accessMonth) {
		PageInfo<ProChargeDay> result = new PageInfo<ProChargeDay>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("logo", logo);
		paramsMap.put("project", project);
		paramsMap.put("apiId", apiId);
		paramsMap.put("chargeType", chargeType);
		paramsMap.put("accessMonth", accessMonth);
		List<ProChargeDay> list = projectChargingDayMapper.selectProChargeDay(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}
	

}
