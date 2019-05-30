package com.bfd.api.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.domain.LabelConfig;
import com.bfd.api.vo.LabelVo;

public interface LabelConfigMapper {

    List<LabelVo> selectByLabelId(@Param("labelId")String[] labelId);
    
    /**
     * 获取所有的标签配置列表
     * @return
     */
    List<LabelConfig> list();
}