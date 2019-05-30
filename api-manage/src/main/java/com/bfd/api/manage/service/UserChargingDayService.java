package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.vo.UserChargingDayVo;

public interface UserChargingDayService {
     
 	PageInfo<UserChargingDayVo> getUserChargingDays(Integer currentPage,
			Integer pageSize,String userId, String userLogo, String userProject, 
			Long apiId, Integer chargeType, String accessMonth);
}
