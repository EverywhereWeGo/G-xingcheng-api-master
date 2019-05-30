package com.bfd.api.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.ChargeRule;
import com.bfd.api.manage.mapper.ChargeRuleMapper;
import com.bfd.api.manage.service.ChargeRuleService;

@Service("chargeRuleService")
@Transactional
public class ChargeRuleServiceImpl implements ChargeRuleService {

	@Autowired
	private ChargeRuleMapper chargeRuleMapper; 

	@Override
	public PageInfo<ChargeRule> getAllChargeRules(Integer currentPage, Integer pageSize) {
		PageInfo<ChargeRule> result = new PageInfo<ChargeRule>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<ChargeRule> list = chargeRuleMapper.selectAllChargeRules(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		Integer totalCount = chargeRuleMapper.count();
		result.setTotalPageNum(totalCount);
		return result;
	}

	@Override
	public void insertChargeRule(Integer spendType, String spendName, Long upperLimit, Long lowerLimit, Long unitPrice,
			Integer isDiscount, Long discount, Integer status, Date addTime, Date updateTime) {
		ChargeRule cr = new ChargeRule();
		cr.setSpendType(spendType);
		cr.setSpendName(spendName);
		cr.setUpperLimit(upperLimit);
		cr.setLowerLimit(lowerLimit);
		cr.setUnitPrice(unitPrice);
		cr.setIsDiscount(isDiscount);
		cr.setDiscount(discount);
		cr.setStatus(status);
		cr.setAddTime(addTime);
		cr.setUpdateTime(updateTime);
		chargeRuleMapper.insert(cr);
	}

}
