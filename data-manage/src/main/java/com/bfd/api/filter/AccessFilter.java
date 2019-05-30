package com.bfd.api.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bfd.api.common.ReturnJson;
import com.bfd.api.utils.Encrypt;
import com.bfd.api.vo.TokenVo;

//@Component
public class AccessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("utf-8");
        HttpServletResponse rep = (HttpServletResponse) response;
        rep.setCharacterEncoding("UTF-8");
        rep.setContentType("application/json");
        rep.setHeader("Cache-Control", "no-cache");
        rep.setHeader("Access-Control-Allow-Origin", "*");
        rep.setHeader("Access-Control-Allow-Headers", "*");
        rep.setHeader("Access-Control-Expose-Headers", "*");
		
        String path = req.getServletPath();
        if(path.contains("/getToken")){
        	chain.doFilter(request, rep);
        	return;
        }
        
        //验证token
		String accessToken = req.getParameter("access_token");
		if(StringUtils.isEmpty(accessToken)){
			this.returnNone(rep, "401", "invalid access token");
			return;
		}
		
		try{
			accessToken = accessToken.replace(" ", "+");
			String token = Encrypt.decrypt(accessToken);
			TokenVo vo = JSONObject.parseObject(token, TokenVo.class);
			
			request.setAttribute("appCode", vo.getAppCode());
			request.setAttribute("appId", vo.getAppId());
			Long expire = vo.getExpire();
			Long now = new Date().getTime();
			if(now>expire){
				this.returnNone(rep, "403", "access token is expire");
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			this.returnNone(rep, "401", "invalid access token");
			return;
		}
		
		chain.doFilter(request, rep);
		return;
	}

	@Override
	public void destroy() {
		
	}

	 private void returnNone(HttpServletResponse response, String code, String msg) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(ReturnJson.getFailedJson(code, msg, msg));
        writer.flush();
        writer.close();
    }
	 
}
