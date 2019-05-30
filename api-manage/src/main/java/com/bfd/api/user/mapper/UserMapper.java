package com.bfd.api.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bfd.api.user.domain.User;

public interface UserMapper {

	int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);
    
    User selectUserByName(@Param("name")String name);

    List<User> selectAllUser();

}