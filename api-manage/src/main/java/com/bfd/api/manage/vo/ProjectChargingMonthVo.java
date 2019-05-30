package com.bfd.api.manage.vo;


public class ProjectChargingMonthVo {

    private String logo;

    private String project;
    private String projName;

    private Long apiId;
    private String apiName;
    
    private Integer chargeType;

    private Long chargeId;
    
    private Long accessCount;   //当月访问统计数
    
    private Long accessFlow;    //当月总流量
    
    private Long dealCount;     //当月总数量

    private Long accessMoney;   //当月花费
    
    private String accessMonth;  //统计月份  格式为2017-08
    
    private String updateTime;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
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

	
}