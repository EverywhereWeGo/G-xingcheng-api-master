package com.bfd.api.service;

import java.util.Map;

import com.bfd.api.domain.App;


public interface AppService {

	public App getAppByCode(String appCode,String appSecret);
	
	public Map<String,Object> getToken(App app)  throws Exception ;
}
