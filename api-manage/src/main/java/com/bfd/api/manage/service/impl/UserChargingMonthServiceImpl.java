package com.bfd.api.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.mapper.UserChargingMonthMapper;
import com.bfd.api.manage.service.UserChargingMonthService;
import com.bfd.api.manage.vo.UserChargeMonth;
import com.bfd.api.manage.vo.UserChargingMonthVo;

@Service("userChargingMonthService")
@Transactional
public class UserChargingMonthServiceImpl implements UserChargingMonthService {

	@Autowired
	private UserChargingMonthMapper userChargingMonthMapper;
	

	@Override
	public PageInfo<UserChargeMonth> getUserChargeMonths(Integer currentPage, Integer pageSize) {
		PageInfo<UserChargeMonth> result = new PageInfo<UserChargeMonth>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<UserChargeMonth> list = userChargingMonthMapper.selectUserChargeMonth(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}


	@Override
	public PageInfo<UserChargingMonthVo> getAllUserChargingMonths(Integer currentPage, Integer pageSize, String userId,
			String userLogo, String userProject, Long apiId, Integer chargeType) {
		PageInfo<UserChargingMonthVo> result = new PageInfo<UserChargingMonthVo>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("userId", userId);
		paramsMap.put("userLogo", userLogo);
		paramsMap.put("userProject", userProject);
		paramsMap.put("apiId", apiId);
		paramsMap.put("chargeType", chargeType);
		List<UserChargingMonthVo> list = userChargingMonthMapper.selectUserChargingMonthById(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}

}
