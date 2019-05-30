package com.bfd.api.manage.vo;

public class ProAccessCount {

	private String logo;      // 业态Id
	private String project;   // 项目Id
	private String proName;   // 项目名称
	private Long apiId;       // api主键
	private String apiName;   // api名称
	private Long totalTimes;  // 项目访问的api总次数
	private Long totalFlow;   // 总流量
	private Long totalNum;    // 总数量
	private String accessDay; // 最新统计时间
	
	
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
	public Long getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(Long totalTimes) {
		this.totalTimes = totalTimes;
	}
	public Long getTotalFlow() {
		return totalFlow;
	}
	public void setTotalFlow(Long totalFlow) {
		this.totalFlow = totalFlow;
	}
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	public String getAccessDay() {
		return accessDay;
	}
	public void setAccessDay(String accessDay) {
		this.accessDay = accessDay;
	}
	
	
}
