package com.bfd.api.vo;

public class LabelVo {

	public String id;
	
	public String labelId;
	
	public String labelName;
	
	public String parentId;
	
	public String hbaseColumn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getHbaseColumn() {
		return hbaseColumn;
	}

	public void setHbaseColumn(String hbaseColumn) {
		this.hbaseColumn = hbaseColumn;
	}
	
}
