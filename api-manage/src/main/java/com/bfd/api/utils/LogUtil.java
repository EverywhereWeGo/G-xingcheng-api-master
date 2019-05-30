package com.bfd.api.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.bfd.api.user.domain.User;
import com.bfd.api.utils.log.OpBehaviorType;
import com.bfd.api.utils.log.OpLog;

public class LogUtil {

	private static Map<String, Logger> logMap = new HashMap<String, Logger>();

	private LogUtil() {
	}

	static {
		logMap.put(LogType.Run.toString(), LoggerFactory.getLogger(LogType.Run.toString()));
		logMap.put(LogType.Ope.toString(), LoggerFactory.getLogger(LogType.Ope.toString()));
		logMap.put(LogType.Beh.toString(), LoggerFactory.getLogger(LogType.Beh.toString()));
		logMap.put(LogType.Ser.toString(), LoggerFactory.getLogger(LogType.Ser.toString()));
	}

	public static Logger getLogger(LogType logtype) {
		return logMap.get(logtype.toString());
	}
	
	
	/**
	 * 设置操作日志
	 */
	public static void setOpInfoLog(HttpServletRequest request,OpBehaviorType behaviorType){
		OpLog op = getOpLog(request,behaviorType, "2200", "");
		LogUtil.getLogger(LogType.Ope).info(JSONObject.toJSONString(op));
	}
	
	public static void setOpErrorLog(HttpServletRequest request,OpBehaviorType behaviorType,Object feedback){
		OpLog op = getOpLog(request, behaviorType, "2500", feedback);
		LogUtil.getLogger(LogType.Ope).error(JSONObject.toJSONString(op),feedback);
	}
	
	protected static OpLog getOpLog(HttpServletRequest request,OpBehaviorType behaviorType,
			String result,Object feedback){
		OpLog op = new OpLog();
		op.setBehaviorContent(JSONObject.toJSONString(request.getParameterMap()));
		op.setBehaviorType(behaviorType.toString());
		op.setFeedback(JSONObject.toJSONString(feedback));
		op.setResult(result);
		op.setItemId("Object");
		op.setAppId("SJGX");
		op.setcTime(String.valueOf(new Date().getTime()));
		op.setIp(request.getRemoteHost());
		op.setParams(JSONObject.toJSONString(request.getParameterMap()));
		User user = (User) request.getSession().getAttribute("session_user");
		if(user!=null){
			op.setUserId(String.valueOf(user.getId()));
		}
		return op;
	}
}
