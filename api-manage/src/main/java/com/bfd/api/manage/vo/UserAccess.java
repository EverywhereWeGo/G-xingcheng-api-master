package com.bfd.api.manage.vo;

public class UserAccess {

    private String userId;
    
    //private String userName;
    
    private String userLogo;
    
    private String userProject;
    
    private String proName;

    private Long apiId;
    
    private String apiName;

    private Long appId;
    
    private String appName;

    private Long accessCount;

    private Long successCount;
    
    private Long accessFlow;
    
    private Long dealCount;

    private String accessDay;

    private String updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getUserProject() {
		return userProject;
	}

	public void setUserProject(String userProject) {
		this.userProject = userProject;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Long getApiId() {
		return apiId;
	}

	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	public Long getAccessFlow() {
		return accessFlow;
	}

	public void setAccessFlow(Long accessFlow) {
		this.accessFlow = accessFlow;
	}

	public Long getDealCount() {
		return dealCount;
	}

	public void setDealCount(Long dealCount) {
		this.dealCount = dealCount;
	}

	public String getAccessDay() {
		return accessDay;
	}

	public void setAccessDay(String accessDay) {
		this.accessDay = accessDay;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}