package com.bfd.api.manage.service;


import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.Api;

public interface ApiService {
   
	//查询所有接口
	PageInfo<Api> getAllApis(Integer currentPage,
			Integer pageSize);
    
	//根据类型查所有接口
	PageInfo<Api> getApiByType(Integer currentPage,
			Integer pageSize,int apiType);
	
	//查询api名字查接口
	PageInfo<Api> getApiByName(Integer currentPage,
			Integer pageSize,String apiName);
	
	//多个条件查询
	PageInfo<Api> getApis(Integer currentPage,
			Integer pageSize,Integer apiType,String apiName,Long apiId);

	//新增API
	void insertApi(String apiName, 
		    int apiType, 
		    int chargeType, 
		    String url, 
		    String descr, 
		    int status);
	//获取所有的API对象
	Api getApiByUrl(String url);
}
