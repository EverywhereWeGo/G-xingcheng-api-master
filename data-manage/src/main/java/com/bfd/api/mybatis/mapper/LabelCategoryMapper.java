package com.bfd.api.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.domain.LabelCategory;

public interface LabelCategoryMapper {
    /**
     * 获取所有的品类列表
     * @return
     */
    List<LabelCategory> list();
    
    LabelCategory getCategoryById(@Param("categoryId")String categoryId);
}