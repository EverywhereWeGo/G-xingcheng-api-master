package com.bfd.api.vo;

public class TokenVo {

	private Long appId;
	private String appCode;
	private Long expire;
	private String accessKey;
	
	public TokenVo() {}
	
	public TokenVo(Long appId, String appCode, Long expire, String accessKey) {
		super();
		this.appId = appId;
		this.appCode = appCode;
		this.expire = expire;
		this.accessKey = accessKey;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public Long getExpire() {
		return expire;
	}
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
