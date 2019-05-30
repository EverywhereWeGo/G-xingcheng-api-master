package com.bfd.api.zuul.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bfd.api.zuul.utils.LogType;
import com.bfd.api.zuul.utils.LogUtil;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import net.sf.json.JSONObject;

public class ApiErrorFilter extends ZuulFilter {

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletResponse response = ctx.getResponse();
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", "200");
		map.put("message", "网关发生错误，请开发者稍候再试!");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(JSONObject.fromObject(map).toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			LogUtil.getLogger(LogType.Run).error("请求api-server异常", e);
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
		return "error";
	}
}
