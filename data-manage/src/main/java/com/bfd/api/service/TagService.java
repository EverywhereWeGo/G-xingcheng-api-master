package com.bfd.api.service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.vo.UserInfo;

public interface TagService {

	public long getUserNum(String userId,
			String userLogo, 
			String userProject, 
			String accessLogo,
			String accessProject,
			String grouping) throws Exception ;
	
	public PageInfo<UserInfo> getUserList(
			String userId,
			String userLogo,
			String userProject,
			String accessLogo,
			String accessProject,
			String grouping,
			String appCode,
			Integer currentPage,
			Integer pageSize) throws Exception;


	public PageInfo<UserInfo> getUserListCommon(Long appId, String json, Integer currentPage, Integer pageSize,Long num) throws Exception;

	public Long getUserNumCommon(Long appId, String json)  throws Exception;

	
}
