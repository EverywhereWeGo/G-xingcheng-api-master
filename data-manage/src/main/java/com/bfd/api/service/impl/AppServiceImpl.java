package com.bfd.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bfd.api.domain.App;
import com.bfd.api.mybatis.mapper.AppMapper;
import com.bfd.api.service.AppService;
import com.bfd.api.utils.Constants;
import com.bfd.api.utils.Encrypt;
import com.bfd.api.vo.TokenVo;

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService {
	
	@Autowired
	AppMapper appMapper;

	@Override
	public App getAppByCode(String appCode,String appSecret) {
		return appMapper.selectByCode(appCode,appSecret);
	}

	@Override
	public Map<String, Object> getToken(App app) throws Exception {
		// 有效期30分钟
		long expires_in = (new Date().getTime()) + Long.valueOf(Constants.TOKEN_EXPIRE);
		TokenVo token = new TokenVo(app.getId(), app.getAppCode(), expires_in, app.getAppSecret().substring(app.getAppSecret().length() - 4));
		String tokenStr = JSONObject.toJSON(token).toString();

		String access_token = Encrypt.encrypt(tokenStr);
		Map<String, Object> success = new HashMap<String, Object>();
		success.put("access_token", access_token);
		success.put("expires_in", expires_in);
		return success;
	}
	
}
