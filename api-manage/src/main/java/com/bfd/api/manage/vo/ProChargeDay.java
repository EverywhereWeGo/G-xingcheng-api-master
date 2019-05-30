package com.bfd.api.manage.vo;

public class ProChargeDay {

	private String logo;
	private String project; // 项目Id
	private String projName; // 项目名称
	private Long apiId; // api主键
	private String apiName; // api名称
	private Integer chargeType; // 计费类型
	private Long accessCount;
	private Long accessFlow;
	private Long dealCount;
	private Long accessMoney;
	private String accessDay;
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