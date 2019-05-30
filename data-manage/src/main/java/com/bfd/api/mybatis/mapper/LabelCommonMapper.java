package com.bfd.api.mybatis.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.vo.CommonLabelVo;

public interface LabelCommonMapper {

	
	ArrayList<CommonLabelVo> selectByLabelId(@Param("labelId")String[] labelId);
    
    
}