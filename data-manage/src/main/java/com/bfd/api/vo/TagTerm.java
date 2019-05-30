package com.bfd.api.vo;

public class TagTerm {

	/**
	 * 三级标签名称
	 */
	public String column;
	
	/**
	 * 四级标签值
	 */
	public String value;
	
	public TagTerm(){};

	public TagTerm(String column, String value) {
		this.column = column;
		this.value = value;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
