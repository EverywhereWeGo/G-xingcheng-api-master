package com.bfd.api.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.domain.LabelInfo;

public interface LabelInfoMapper {
    LabelInfo getLabelInfoByLabelId(@Param("labelId")String labelId);
}