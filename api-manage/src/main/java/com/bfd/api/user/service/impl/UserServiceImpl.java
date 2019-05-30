package com.bfd.api.user.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.user.domain.User;
import com.bfd.api.user.mapper.UserMapper;
import com.bfd.api.user.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	public UserMapper userMapper;
	
	@Override
	public User info(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void login(String name, String pwd) {
		
	}

	@Override
	public void logout(Long id) {
		
	}

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public void delUserByid(Long id) {
        userMapper.deleteByPrimaryKey(id);
        
    }

    @Override
    public void updateUserById(User u) {
        userMapper.updateByPrimaryKey(u);       
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
        
    }

}
