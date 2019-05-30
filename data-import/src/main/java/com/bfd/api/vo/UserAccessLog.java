package com.bfd.api.vo;

public class UserAccessLog {
	
	private String custId;
	private String custLogo;
	private String custProject;
	private String accessId;
	private String accessLogo;
	private String accessProject;
	private String status;
	private String cdate;
	private String appCode;
	private String udate;
	
	public String getUdate() {
		return udate;
	}
	public void setUdate(String udate) {
		this.udate = udate;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustLogo() {
		return custLogo;
	}
	public void setCustLogo(String custLogo) {
		this.custLogo = custLogo;
	}
	public String getCustProject() {
		return custProject;
	}
	public void setCustProject(String custProject) {
		this.custProject = custProject;
	}
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	public String getAccessLogo() {
		return accessLogo;
	}
	public void setAccessLogo(String accessLogo) {
		this.accessLogo = accessLogo;
	}
	public String getAccessProject() {
		return accessProject;
	}
	public void setAccessProject(String accessProject) {
		this.accessProject = accessProject;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
