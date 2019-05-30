package com.bfd.api.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.manage.domain.Api;

public interface ApiMapper {
    
    //api接口列表
    List<Api> selectAllApi(Map<String, Object> paramsMap);
    
    //总接口数
    int count();
    
    //接口类型查询
    List<Api> selectApiByType(Map<String, Object> paramsMap);
    
    //api名查询
    List<Api> selectApiByName(Map<String, Object> paramsMap);
    
    
    //多个条件查询
    List<Api> selectApis(Map<String, Object> paramsMap);
    
    //新增API
    void insert(Map<String, Object> paramsMap);
    
    
    //查找API地址
  	Api getApiByUrl(String url);
    
}