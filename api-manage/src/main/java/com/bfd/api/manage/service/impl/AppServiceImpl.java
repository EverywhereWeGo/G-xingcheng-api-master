package com.bfd.api.manage.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfd.api.manage.domain.App;
import com.bfd.api.manage.mapper.AppMapper;
import com.bfd.api.manage.service.AppService;


@Service("appService")
@Transactional
public class AppServiceImpl implements AppService {

	@Autowired
	private AppMapper appMapper;
	
	@Override
	public App get(Long id) {
		return appMapper.selectByPrimaryKey(id);
	}

}
