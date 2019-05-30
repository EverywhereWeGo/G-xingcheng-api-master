package com.bfd.api.vo;

public class CommonLabelVo {

	
	public Long id;
	public String name;
	public String code;
	public Long parentId;
	public Long label_level;
	public Long level;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getLabel_level() {
		return label_level;
	}
	public void setLabel_level(Long label_level) {
		this.label_level = label_level;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	

}
