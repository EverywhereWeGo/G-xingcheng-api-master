package com.bfd.api.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfd.api.common.vo.Result;
import com.bfd.api.user.domain.User;
import com.bfd.api.utils.LogUtil;
import com.bfd.api.utils.log.OpBehaviorType;

public abstract class BaseController {
	 @Resource
	 protected HttpServletRequest request;
	 @Resource
	 protected HttpServletResponse response;
	 
	 public HttpServletRequest getRequest() {
	        return request;
	    }

	    public void setRequest(HttpServletRequest request) {
	        this.request = request;
	    }

	    public HttpServletResponse getResponse() {
	        return response;
	    }

	    public void setResponse(HttpServletResponse response) {
	        this.response = response;
	    }
	    
	    
	    /**
		 * 操作結果
		 * 
		 * @param code
		 * @param msg
		 * @param data
		 * @return
		 */
		protected Result<Object> optResult(String code, String msg, Object data) {
			Result<Object> result = new Result<Object>();
			result.setCode(code);
			result.setMsg(msg);
			result.setData(data);
			return result;
		}
		
		/**
		 * 操作成功
		 * 
		 * @return
		 */
		protected Result<Object> optSuccess() {
			Result<Object> result = new Result<Object>();
			result.setCode("000000");
			result.setMsg("success");
			return result;
		}

		/**
		 * 操作错误
		 * 
		 * @return
		 */
		protected Result<Object> optError() {
			Result<Object> result = new Result<Object>();
			result.setCode("000001");
			result.setMsg("error");
			return result;
		}
		/**
		 * 获取登录用户
		 * 
		 * @return
		 */
		protected User getCurrentUser(){
			return (User) request.getSession().getAttribute("session_user");
		}
		
		
		/**
		 * 设置操作日志
		 */
		protected void setOpErrorLog(OpBehaviorType behaviorType,Object feedback){
			LogUtil.setOpErrorLog(request, behaviorType, feedback);
		}
}
