package com.bfd.api.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.manage.domain.App;

public interface AppMapper {

    int deleteByPrimaryKey(Long id);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
}