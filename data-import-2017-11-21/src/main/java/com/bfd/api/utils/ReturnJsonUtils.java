package com.bfd.api.utils;

import com.bfd.api.common.vo.Result;


public class ReturnJsonUtils {
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
	
	public static Result<Object> getFailedJson(String code, String value)
	{
		Result<Object> ret = new Result<Object>(code, value, "failed");
		return ret;
	}
	
	public static Result<Object> getFailedJson(String code, String msg, Object value)
	{
		Result<Object> ret = new Result<Object>(code, msg, value);
		return ret;
	}
	
}
