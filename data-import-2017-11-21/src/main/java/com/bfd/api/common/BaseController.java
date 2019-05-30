package com.bfd.api.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bfd.api.common.vo.PageInfo;
import com.bfd.api.common.vo.Result;

import net.sf.json.JSONObject;

public abstract class BaseController {
	
	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
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
	 * @return
	 */
	protected Result<Object> optResult(String code, String msg) {
		Result<Object> result = new Result<Object>();
		result.setCode(code);
		result.setMsg(msg);
		return result;
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
		result.setCode("1");
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
		result.setCode("0");
		result.setMsg("error");
		return result;
	}
	
	  /**
     * 返回结果
     * @param resultType
     * @param result
     */
    public void writeResult(Object result){
        response.setHeader("api-size",result.toString().getBytes().length+"");
        Integer count = 0;
        try {
         	if(result instanceof Result){
        		PageInfo<Object> data = (PageInfo<Object>) ((Result<Object>)result).getData();
        		if(data.getTotalList()!=null){
        			count = data.getTotalList().size();
        		}
        	}
		} catch (Exception e) {
			logger.error("", "api请求返回结果异常  count 不存在", e);
		}
        response.setHeader("api-count",count!=null?count.toString():"0");
        this.writeJson(result);
        return;
    }
    
    public  void writeJson(Object object) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        if((object instanceof Map) || (object instanceof Result)){
            String output = JSONObject.fromObject(object).toString();
            writeJson(output);
        }else if (object instanceof String){
            String output = (String) object;
            writeJson(output);
        }
    }

    private void writeJson(String output){
        try {
            PrintWriter o= response.getWriter();
            o.println(output);
            o.flush();
            o.close();
        } catch (IOException e1) {
        	logger.error("", "api请求返回json结果异常", e1);
        }
    }
}
