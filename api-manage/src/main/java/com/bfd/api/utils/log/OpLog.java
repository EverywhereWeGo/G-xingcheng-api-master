package com.bfd.api.utils.log;

/**
 * 操作日志
 * @author BFD_523
 *
 */
public class OpLog {
	
	private String appId;
	
	private String userId;
	
	private String itemId;
	
	private String behaviorType;
	
	private String cTime;
	
	private String ip;
	
	private String behaviorContent;
	
	private String result;
	
	private String feedback;
	
	private String params;
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBehaviorContent() {
		return behaviorContent;
	}

	public void setBehaviorContent(String behaviorContent) {
		this.behaviorContent = behaviorContent;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	

}
