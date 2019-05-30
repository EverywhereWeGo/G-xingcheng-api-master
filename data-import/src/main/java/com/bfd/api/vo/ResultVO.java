package com.bfd.api.vo;

public class ResultVO {

	private int code = 0;
	private String message = "";

	@Override
	public String toString() {
		return "code=" + code + ", message=" + message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
