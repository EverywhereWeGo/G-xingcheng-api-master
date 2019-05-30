package com.bfd.api.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bfd.api.common.BaseController;
import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;
import com.bfd.api.manage.domain.ChargeRule;
import com.bfd.api.manage.service.ChargeRuleService;
import com.bfd.api.utils.ReturnJsonUtils;

@RestController
@RequestMapping(value = "/charge",produces = { "application/json;charset=UTF-8" })
@ResponseBody
public class ChargeRuleController extends BaseController {
	@Autowired
	public ChargeRuleService chargeRuleService;
	
	@RequestMapping(value = "/getAll")  
	public Result<Object> get(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		try {
			PageInfo<ChargeRule> chargeRules = chargeRuleService.getAllChargeRules(currentPage, pageSize);
			return ReturnJsonUtils.getSuccessJson("1", chargeRules);
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnJsonUtils.getFailedJson("0", "系统错误");
		}
	}
	
	
	@RequestMapping(value = "/insert")  
	public void get(@RequestParam(value = "spendType", required = true) Integer spendType,
			@RequestParam(value = "spendName", required = true) String spendName,
			@RequestParam(value = "upperLimit", required = true) Long upperLimit,
			@RequestParam(value = "lowerLimit", required = true) Long lowerLimit,
			@RequestParam(value = "unitPrice", required = true) Long unitPrice,
			@RequestParam(value = "isDiscount", required = true) Integer isDiscount,
			@RequestParam(value = "discount", required = true) Long discount,
			@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "addTime", required = true) String addTime,
			@RequestParam(value = "updateTime", required = true) String updateTime){
		try {
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Long add_Time = Long.parseLong(addTime);
		    Long update_Time = Long.parseLong(updateTime);
		    String d1 = format.format(add_Time); 
		    String d2 = format.format(update_Time); 
		    Date date1=format.parse(d1);
		    Date date2=format.parse(d2);
			chargeRuleService.insertChargeRule(spendType, spendName, 
					upperLimit, lowerLimit, unitPrice, isDiscount, 
					discount, status, date1, date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
