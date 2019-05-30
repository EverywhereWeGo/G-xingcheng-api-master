package com.bfd.api.manage.domain;


public class ProjectAccessStatistics {
	
    private Long id;

    private String logo;

    private String project;

    private Long apiId;

    private Long accessCount;
    
    private Long successCount;

    private Long accessFlow;
    
    private Long dealCount;  
    
    private String accessDay;

    private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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