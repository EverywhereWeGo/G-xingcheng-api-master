package com.bfd.api.manage.vo;

public class ChargeRuleVo {
	private Long id;

    private String spendType; //计费类型：0 数量计费 1 流量计费

    private String spendName;

    private Long upperLimit;

    private Long lowerLimit;

    private Long unitPrice;

    private String isDiscount; //是否优惠：0 无优惠 1 有优惠

    private Long discount;

    private String status;  //启用状态：0 不在使用，1 使用中

    private String addTime;

    private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpendType() {
		return spendType;
	}

	public void setSpendType(String spendType) {
		this.spendType = spendType;
	}

	public String getSpendName() {
		return spendName;
	}

	public void setSpendName(String spendName) {
		this.spendName = spendName;
	}

	public Long getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Long upperLimit) {
		this.upperLimit = upperLimit;
	}

	public Long getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Long lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
