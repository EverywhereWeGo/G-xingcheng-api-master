package com.bfd.api.manage.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ProjectAccessStatistics;
import com.bfd.api.manage.domain.ProjectChargingMonth;
import com.bfd.api.manage.mapper.ProjectChargingMonthMapper;
import com.bfd.api.manage.service.ProjectChargingMonthService;
import com.bfd.api.manage.vo.ProChargeMonth;
import com.bfd.api.manage.vo.ProjectChargingMonthVo;

@Service("projectChargingMonthService")
@Transactional
public class ProjectChargingMonthServiceImpl implements ProjectChargingMonthService {

	@Autowired
	private ProjectChargingMonthMapper projectChargingMonthMapper;

	@Override
	public PageInfo<ProChargeMonth> getProChargeMonths(Integer currentPage, Integer pageSize) {
		PageInfo<ProChargeMonth> result = new PageInfo<ProChargeMonth>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<ProChargeMonth> list = projectChargingMonthMapper.selectProAccessMonth(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}

	@Override
	public PageInfo<ProjectChargingMonthVo> getProjectChargingMonths(Integer currentPage, Integer pageSize, String logo,
			String project, Long apiId, Integer chargeType) {
		PageInfo<ProjectChargingMonthVo> result = new PageInfo<ProjectChargingMonthVo>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("logo", logo);
		paramsMap.put("project", project);
		paramsMap.put("apiId", apiId);
		paramsMap.put("chargeType", chargeType);
		List<ProjectChargingMonthVo> list = projectChargingMonthMapper.selectProAccessMonthByLogo(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}
	
	

	
}
