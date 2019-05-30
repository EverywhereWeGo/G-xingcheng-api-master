package com.bfd.api.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.domain.App;


public interface AppMapper {
    
    App selectByCode(@Param("appCode")String appCode,@Param("appSecret")String appSecret);

}