package com.bfd.api.common.vo;

public class Result<T> {
	public Result(){}
    public Result(String code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private String code ;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
