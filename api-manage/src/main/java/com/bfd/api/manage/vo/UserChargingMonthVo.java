package com.bfd.api.manage.vo;


public class UserChargingMonthVo {

    private String userId;
    //private String userName;
    private String userLogo;
    private String userProject;
    private String projName;
    private Long apiId;
    private String apiName;
    private Integer chargeType;
    private Long chargeId;
    private Long accessCount;
    private Long accessFlow;
    private Long dealCount;
    private Long accessMoney;
    private String  accessMonth;
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
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
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
	public Long getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public Long getChargeId() {
		return chargeId;
	}
	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}
	public Long getAccessMoney() {
		return accessMoney;
	}
	public void setAccessMoney(Long accessMoney) {
		this.accessMoney = accessMoney;
	}
	public String getAccessMonth() {
		return accessMonth;
	}
	public void setAccessMonth(String accessMonth) {
		this.accessMonth = accessMonth;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
    
	
}