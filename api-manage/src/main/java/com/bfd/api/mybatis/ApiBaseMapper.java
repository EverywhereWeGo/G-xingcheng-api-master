package com.bfd.api.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的Mapper
 */
public interface ApiBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
