package com.bfd.api.manage.domain;


public class UserChargingMonth {
    private Long id;

    private String userId;

    private String userLogo;

    private String userProject;

    private Long apiId;

    private Integer chargeType;

    private Long chargeId;

    private Long accessCount;
    
    private Long accessFlow;
    
    private Long dealCount;
    
    private Long accessMoney;
    
    private String accessMonth;
    
    private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public Long getApiId() {
		return apiId;
	}

	public void setApiId(Long apiId) {
		this.apiId = apiId;
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