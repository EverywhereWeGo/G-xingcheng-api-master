package com.bfd.api.manage.vo;

public class ProjectAccess {

	private String logo;      // 业态Id
	private String project;   // 项目Id
	private String proName;   // 项目名称
	private Long apiId;       // api主键
	private String apiName;   // api名称
    private Long accessCount;
    private Long successCount;
    private Long accessFlow;
    private Long dealCount;  
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
