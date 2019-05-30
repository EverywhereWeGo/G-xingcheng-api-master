package com.bfd.api.zuul.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bfd.api.zuul.model.TokenVo;
import com.bfd.api.zuul.service.ManageClient;
import com.bfd.api.zuul.utils.ConfigUtil;
import com.bfd.api.zuul.utils.Encrypt;
import com.bfd.api.zuul.utils.HttpRequestUtil;
import com.bfd.api.zuul.utils.LogType;
import com.bfd.api.zuul.utils.LogUtil;
import com.bfd.api.zuul.utils.RedisUtil;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import net.sf.json.JSONObject;

public class ApiFilter extends ZuulFilter {

	@Autowired
	ManageClient manageClient;

	@Override
	public Object run() {
		//获取上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		//待解决的
		ctx.set("start", System.currentTimeMillis());
//		ctx.set("start",Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		response.setContentType("application/json;charset=utf-8");
		//解决json跨域的问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		ctx.set("status", 0);
		
		String ip = getRemoteAddr(request);
		String url = request.getRequestURI();
		
		LogUtil.getLogger(LogType.Run).info("url=" + url);
		ctx.set("ip", ip);
		ctx.set("url", url);

		String userId = StringUtils.isEmpty(request.getParameter("userId"))?"-":request.getParameter("userId");         //request参数获取
	    String userLogo = StringUtils.isEmpty(request.getParameter("userLogo"))?"-":request.getParameter("userLogo");      //request参数获取
	    String userProject = StringUtils.isEmpty(request.getParameter("userProject"))?"-":request.getParameter("userProject");
	    
	    ctx.set("userId",userId);
	    ctx.set("userLogo",userLogo);
	    ctx.set("userProject",userProject);
		
	    JSONObject api = getApi(ctx,url);
	    
	    if(api==null){
	    	setError(ctx, response, "无效的URL");
	    	return null;
	    }
	    
	    if(url.contains("/data/getToken")){
	    	ctx.set("status", 1);
	    	ctx.set("appCode",request.getParameter("appCode"));
			return null;
	    }
	    
		String access_token = request.getParameter("access_token");
		if (StringUtils.isEmpty(access_token)) {
			setError(ctx, response, "access_token为空");
			return null;
		}
		access_token = access_token.replace(" ", "+");
		LogUtil.getLogger(LogType.Run).info("access_token=" + access_token);
		
		ctx.set("accessKey",access_token);
		// 获取APP的参数
		TokenVo token = getToken(ctx, access_token);
		// 验证 access_token
		if (token == null) {
			setError(ctx, response, "无效的access_token");
			return null;
		}
		request.setAttribute("appId", token.getAppId());
		request.setAttribute("appCode", token.getAppCode());
		
		// 验证 有效期
		if (token.getExpire() < System.currentTimeMillis()) {
			setError(ctx, response, "access_token已过期");
			return null;
		}
		
		//URL是完全可以正确查询的，获取API的类型为了作统计API类型,需要修改数据
		ctx.set("apiId", api==null?"-1":api.get("id"));
		ctx.set("apiType", api==null?"-1":api.get("apiType"));
		ctx.set("chargeType", api==null?"-1":api.get("chargeType"));
		ctx.set("status", api==null?"-1":1);
		return null;
	}
	
//	private JSONObject getApiByUrl(RequestContext ctx,String url){
//		String json = HttpRequestUtil.doGet(ConfigUtil.getInstance().get("bfd.bdos.url")+url);
//		LogUtil.getLogger(LogType.Run).info(json);
//		JSONObject result = JSONObject.fromObject(json);
//		return result;
//	}
	private String getApiByUrl(String url){
		String json = HttpRequestUtil.doGet(ConfigUtil.getInstance().get("bfd.bdos.url")+url);
		return json;
	}
	private JSONObject getApi(RequestContext ctx, String url){
		JSONObject object = new JSONObject();
			String key = "api"+url.replaceAll("/", "_");
			String json = RedisUtil.get(key);
			try{
			if (StringUtils.isEmpty(json)){
					json = getApiByUrl(url);
					object = JSONObject.fromObject(json);
					RedisUtil.set(key,json);
					RedisUtil.expire(key, 1800); // 缓存半小时
			}else{
					object = JSONObject.fromObject(json);
			}}catch(Exception e){
				e.printStackTrace();
			}
		return object;
	}
	
	// 解析 access_token	
	private TokenVo getToken(RequestContext ctx, String accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}
		try {
			String token = Encrypt.decrypt(accessToken);
			JSONObject obj = JSONObject.fromObject(token);
			Long appId = obj.getLong("appId");
			String appCode = obj.getString("appCode");
			Long expire = obj.getLong("expire");
			String accessKey = obj.getString("accessKey");

			ctx.set("appId", appId);
			ctx.set("appCode", appCode);
			ctx.set("accessKey", accessKey);

			TokenVo vo = new TokenVo();
			vo.setAppId(appId);
			vo.setAppCode(appCode);
			vo.setExpire(expire);
			vo.setAccessKey(accessKey);
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

//	// 验证URL(忽略不需要验证的URL)
//	private boolean isNoAuth(RequestContext ctx, int api_id) {
//		String key = "zuul-no-auth-url";
//		String json = RedisUtil.get(key);
//		if (json == null) {
//			// 获取不需要验证的URL
//			try {
//				json = manageClient.getNoAuthAPIList();
//			} catch (Exception e) {
//				LogUtil.getLogger(LogType.Run).error("查询不需要验证的权限列表失败", e);
//			}
//			RedisUtil.set(key, json == null ? "" : json);
//			RedisUtil.expire(key, 120); // 缓存120秒
//		}
//		if (json == null || json.equals("")) {
//			return false;
//		}
//
//		try {
//			JSONArray array = JSONArray.fromObject(json);
//			for (int i = 0; i < array.size(); i++) {
//				JSONObject obj = array.getJSONObject(i);
//				if (api_id == obj.getInt("apiId")) {
//					return true;
//				}
//			}
//		} catch (Exception e) {
//			LogUtil.getLogger(LogType.Run).error("验证不需要权限列表失败", e);
//		}
//		return false;
//	}

	// 设置 错误信息
	private void setError(RequestContext ctx, HttpServletResponse response, String message) {
		LogUtil.getLogger(LogType.Run).info(message + " zuul过滤异常");
		ctx.set("status", 0);
		ctx.set("message", message);
		ctx.setSendZuulResponse(false);
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", "200");
		map.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(JSONObject.fromObject(map).toString());
			out.flush();
			out.close();
		} catch (IOException ex) {
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}
	
}
