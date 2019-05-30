package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import com.bfd.api.manage.domain.ChargeRule;

public interface ChargeRuleMapper {

    //查询所有的规则
    List<ChargeRule> selectAllChargeRules(Map<String, Object> paramsMap);
    
    //总规则数
    int count();
    
    //增加规则
    void insert(ChargeRule record);

    //更新规则
    int updateByPrimaryKey(ChargeRule record);
}