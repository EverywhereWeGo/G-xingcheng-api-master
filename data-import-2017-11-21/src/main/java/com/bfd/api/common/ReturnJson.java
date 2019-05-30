package com.bfd.api.common;

import com.alibaba.fastjson.JSONObject;
import com.bfd.api.common.vo.Result;


public class ReturnJson {
	
	public static Result<Object> getSuccessJson(Object value)
	{
		Result<Object> ret = new Result<Object>("1", "success", value);
		return ret;
	}
	public static Result<Object> getSuccessJson(String code, Object value)
	{
		Result<Object> ret = new Result<Object>(code, "success", value);
		return ret;
	}
	public static Result<Object> getSuccessJson(String code, String msg, Object value)
	{
		Result<Object> ret = new Result<Object>(code, msg, value);
		return ret;
	}
	
	public static Result<Object> getFailedJson()
	{
		Result<Object> ret = new Result<Object>("0", "system error", "");
		return ret;
	}
	
	public static String getFailedJson(String code, String msg, Object value)
	{
		Result<Object> ret = new Result<Object>(code, msg, value);
		return JSONObject.toJSONString(ret);
	}
	
}
