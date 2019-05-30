package com.bfd.api.user.service;


import java.util.List;

import com.bfd.api.user.domain.User;

public interface UserService {

	public User info(Long id);
	
	public void login(String name,String pwd);
	
	public void logout(Long id);

    public List<User> getAllUser();

    public void delUserByid(Long id);

    public void updateUserById(User u);

    public void addUser(User user);
}
