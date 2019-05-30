package com.bfd.api.manage.service;

import java.util.Date;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ChargeRule;

public interface ChargeRuleService {
    
    PageInfo<ChargeRule> getAllChargeRules(Integer currentPage,
			Integer pageSize);
    
    void insertChargeRule(Integer spendType,String spendName,
    		Long upperLimit,Long lowerLimit,Long unitPrice,Integer isDiscount,
    		Long discount,Integer status,Date addTime,Date updateTime);
}
