package com.bfd.api.zuul.model;

public class AccessLog {
	
	private String userId;         //request参数获取
    private String userLogo;      //request参数获取
    private String userProject;   //request参数获取
    private Long apiId;           //api号
    private Integer apiType; 
    private Integer chargeType;   //收费类型
    private Long appId;           //app主键
    private String appCode;       //app编号
    private String accessKey;     
    private String url;          //URL地址
    private String ip;
    private String inParam;      //request获取
    private Long useTime;
    private Integer status;
    private String message;
    private Long accessFlow;
    private Long dealCount; //使用数量
    private Long addTime;   //接口访问时间
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserLogo() {
		return userLogo;
	}
	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}
	public String getUserProject() {
		return userProject;
	}
	public void setUserProject(String userProject) {
		this.userProject = userProject;
	}
	public Long getApiId() {
		return apiId;
	}
	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}
	public Integer getApiType() {
		return apiType;
	}
	public void setApiType(Integer apiType) {
		this.apiType = apiType;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getChargeType() {
		return chargeType;
	}
	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}
	public String getInParam() {
		return inParam;
	}
	public void setInParam(String inParam) {
		this.inParam = inParam;
	}
	public Long getUseTime() {
		return useTime;
	}
	public void setUseTime(Long useTime) {
		this.useTime = useTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public Long getAddTime() {
		return addTime;
	}
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

    
}