package com.bfd.api.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.manage.domain.Api;
import com.bfd.api.manage.mapper.ApiMapper;
import com.bfd.api.manage.service.ApiService;

@Service("apiService")
@Transactional
public class ApiServiceImpl implements ApiService {

	@Autowired
	private ApiMapper apiMapper;

	@Override
	public PageInfo<Api> getAllApis(Integer currentPage, Integer pageSize) {
		PageInfo<Api> result = new PageInfo<Api>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		List<Api> list = apiMapper.selectAllApi(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		Integer totalCount = apiMapper.count();
		result.setTotalPageNum(totalCount);
		return result;
	}


	@Override
	public void insertApi(String apiName, int apiType, 
			int chargeType, String url, String descr, int status) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("apiName", apiName);
		paramsMap.put("apiType", apiType);
		paramsMap.put("chargeType", chargeType);
		paramsMap.put("url", url);
		paramsMap.put("descr", descr);
		paramsMap.put("status", status);
		apiMapper.insert(paramsMap);
	}


	@Override
	public PageInfo<Api> getApiByType(Integer currentPage, Integer pageSize, int apiType) {
		PageInfo<Api> result = new PageInfo<Api>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("apiType", apiType);
		List<Api> list = apiMapper.selectApiByType(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		Integer totalCount = apiMapper.count();
		result.setTotalPageNum(totalCount);
		return result;
	}

	@Override
	public PageInfo<Api> getApiByName(Integer currentPage, Integer pageSize, String apiName) {
		PageInfo<Api> result = new PageInfo<Api>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("apiName", apiName);
		List<Api> list = apiMapper.selectApiByType(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		Integer totalCount = apiMapper.count();
		result.setTotalPageNum(totalCount);
		return result;
	}

	@Override
	public PageInfo<Api> getApis(Integer currentPage, Integer pageSize, 
			Integer apiType, String apiName, Long apiId) {
		PageInfo<Api> result = new PageInfo<Api>(currentPage,pageSize);
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("pageSize", pageSize);
		paramsMap.put("pageNo", result.getPageNo());
		paramsMap.put("apiType", apiType);
		paramsMap.put("apiName", apiName);
		paramsMap.put("apiId", apiId);
		List<Api> list = apiMapper.selectApis(paramsMap);
		result.setCurrentPage(currentPage);
		result.setPageSize(pageSize);
		result.setTotalList(list);
		result.setTotalPageNum(list.size());
		return result;
	}



	@Override
	public Api getApiByUrl(String url) {
		Api result = apiMapper.getApiByUrl(url);
		return result;
	}


	
	
	

}
