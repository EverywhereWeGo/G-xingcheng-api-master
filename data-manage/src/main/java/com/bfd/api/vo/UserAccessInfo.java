package com.bfd.api.vo;

import java.util.List;

public class UserAccessInfo {

	private String custId;
	private String custLogo;
	private String custProject;
	private List<String> accessLogo;
	private List<String> accessProject;
	private List<UserAccessLog> list;
	
	public UserAccessInfo() {}
	
	public UserAccessInfo(String custId) {
		this.custId = custId;
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
	public List<UserAccessLog> getList() {
		return list;
	}
	public void setList(List<UserAccessLog> list) {
		this.list = list;
	}

	public String getCustProject() {
		return custProject;
	}

	public void setCustProject(String custProject) {
		this.custProject = custProject;
	}

	public List<String> getAccessLogo() {
		return accessLogo;
	}

	public void setAccessLogo(List<String> accessLogo) {
		this.accessLogo = accessLogo;
	}

	public List<String> getAccessProject() {
		return accessProject;
	}

	public void setAccessProject(List<String> accessProject) {
		this.accessProject = accessProject;
	}

}
