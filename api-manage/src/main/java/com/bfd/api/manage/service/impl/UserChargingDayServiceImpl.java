package com.bfd.api.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.mapper.UserChargingDayMapper;
import com.bfd.api.manage.service.UserChargingDayService;
import com.bfd.api.manage.vo.UserChargingDayVo;


@Service("userChargingDayService")
@Transactional
public class UserChargingDayServiceImpl implements UserChargingDayService {

	@Autowired
	private UserChargingDayMapper userChargingDayMapper;
	
	@Override
	public PageInfo<UserChargingDayVo> getUserChargingDays(Integer currentPage, Integer pageSize, String userId,
		String userLogo, String userProject, Long apiId, Integer chargeType, String accessMonth) {
		PageInfo<UserChargingDayVo> result = new PageInfo<UserChargingDayVo>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("userId", userId);
		paramsMap.put("userLogo", userLogo);
		paramsMap.put("userProject", userProject);
		paramsMap.put("apiId", apiId);
		paramsMap.put("chargeType", chargeType);
		paramsMap.put("accessMonth", accessMonth);
		List<UserChargingDayVo> list = userChargingDayMapper.selectUserChargingDays(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		Integer totalCount = userChargingDayMapper.count();
		result.setTotalPageNum(list.size());
		return result;
	}

}
