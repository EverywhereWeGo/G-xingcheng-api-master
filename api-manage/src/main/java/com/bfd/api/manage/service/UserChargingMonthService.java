package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.vo.UserChargeMonth;
import com.bfd.api.manage.vo.UserChargingMonthVo;

public interface UserChargingMonthService {
     
 	PageInfo<UserChargingMonthVo> getAllUserChargingMonths(Integer currentPage,
			Integer pageSize,String userId,
			String userLogo,String userProject,Long apiId,Integer chargeType);
 	
 	//分页月统计
 	PageInfo<UserChargeMonth> getUserChargeMonths(Integer currentPage,
			Integer pageSize);
}
