package com.bfd.api.zuul.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.ReflectionUtils;
import com.bfd.api.zuul.model.AccessLog;
import com.bfd.api.zuul.service.KafkaProducer;
import com.bfd.api.zuul.utils.LogType;
import com.bfd.api.zuul.utils.LogUtil;
import com.google.common.collect.Maps;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import net.sf.json.JSONObject;

public class ApiPostFilter extends ZuulFilter {

	@Override
	public Object run() {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();

		try {
			Object e = ctx.get("error.exception");
			if (e != null && e instanceof ZuulException) {
				ctx.set("status", 0);
				Map<String, Object> map = Maps.newHashMap();
				map.put("code", "100");
				map.put("message", "服务发生错误，请开发者稍候再试!");
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println(JSONObject.fromObject(map).toString());
					out.flush();
					out.close();
				} catch (IOException ex) {
					LogUtil.getLogger(LogType.Run).error("请求server异常", ex);
				}
			}
		}catch (Exception ex) {
			ReflectionUtils.rethrowRuntimeException(ex);
		}finally {
			String url = (String) ctx.get("url");
			long start = (Long) ctx.get("start");
			String ip = (String) ctx.get("ip");
			String userId = (String) ctx.get("userId");
			String userLogo = (String) ctx.get("userLogo");
			String userProject = (String) ctx.get("userProject");
			Integer chargeType = (Integer) ctx.get("chargeType");
			Long appId = 0l;
			if(ctx.get("appId") != null){
				appId = Long.valueOf(ctx.get("appId").toString());
			}
			
			String appCode = (ctx.get("appCode") == null ? "" : ctx.get("appCode").toString());
			String accessKey = (ctx.get("accessKey") == null ? "" : ctx.get("accessKey").toString());
			int status = (ctx.get("status") == null ? 0 : Integer.valueOf(ctx.get("status").toString()));
			
			Long apiId = 0l;
			if(ctx.get("apiId") != null){
				apiId = Long.valueOf(ctx.get("apiId").toString());
			}
			
			int apiType =  (ctx.get("apiType") == null ? 0 : Integer.valueOf(ctx.get("apiType").toString()));
			String message =  (ctx.get("message") == null ? "" : ctx.get("message").toString());
			long useTime = System.currentTimeMillis() - start;
			String in_param = getParams(request);
			Long flow = flow();
			Long count = count();

			AccessLog log = new AccessLog();
		    log.setUserId(userId);
		    log.setUserLogo(userLogo);
		    log.setUserProject(userProject);
		    log.setApiId(apiId);
		    log.setApiType(apiType);
		    
		    if(ctx.get("chargeType") != null ){
		    	 log.setChargeType(Integer.valueOf(ctx.get("chargeType").toString()));
		    }
		    log.setAccessKey(accessKey);
		    log.setAddTime(start);
		    log.setApiId(apiId);   
		    log.setApiType(apiType);  
		    log.setAppCode(appCode);  
		    log.setAppId(appId);     
		    log.setInParam(in_param); 
		    log.setIp(ip);
		    log.setMessage(message);
		    log.setStatus(status);
		    log.setUrl(url);
		    log.setUserId(userId);
		    log.setUserLogo(userLogo);
		    log.setUserProject(userProject);
		    log.setUseTime(useTime);
		    log.setChargeType(chargeType);
		    log.setAccessFlow(flow);
		    log.setDealCount(count);  //没数据
			KafkaProducer.send(JSONObject.fromObject(log).toString());
		}
		return null;
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
		return "post";
	}

	private static String getParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return JSONObject.fromObject(map).toString();
	}
	
	private Long flow(){
		RequestContext ctx = RequestContext.getCurrentContext();
		List<com.netflix.util.Pair<String, String>> list = ctx.getZuulResponseHeaders();
		for(Pair<String,String> pair:list){
			if(pair==null){
				continue;
			}
			if("api-size".equals(pair.first())){
				@SuppressWarnings("unused")
				boolean r = list.remove(pair);
				return Long.valueOf(pair.second()==null?"0":pair.second());
			}
		}
		return 0l;
	}
	
	private Long count(){
		RequestContext ctx = RequestContext.getCurrentContext();
		List<com.netflix.util.Pair<String, String>> list = ctx.getZuulResponseHeaders();
		for(Pair<String,String> pair:list){
			if(pair==null){
				continue;
			}
			if("api-count".equals(pair.first())){
				@SuppressWarnings("unused")
				boolean r = list.remove(pair);
				return Long.valueOf(pair.second()==null?"0":pair.second());
			}
		}
		return 0l;
	}
}
