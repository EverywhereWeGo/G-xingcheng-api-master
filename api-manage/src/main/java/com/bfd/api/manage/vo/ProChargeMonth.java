package com.bfd.api.manage.vo;

public class ProChargeMonth {
    
	private String logo;           //业态
	private String project;        // 项目Id
	private String proName;      // 项目名称
	private Long apiId;          // api主键
	private String apiName;      // api名称
	private Integer chargeType;  //计费类型
	private Long totalCost;      //总花费   
	private String accessMonth;  //最新统计 月份
	
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
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public Long getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Long totalCost) {
		this.totalCost = totalCost;
	}
	public String getAccessMonth() {
		return accessMonth;
	}
	public void setAccessMonth(String accessMonth) {
		this.accessMonth = accessMonth;
	}
	
}