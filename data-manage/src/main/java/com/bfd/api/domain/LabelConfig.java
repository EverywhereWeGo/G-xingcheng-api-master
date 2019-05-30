package com.bfd.api.domain;

import java.util.Date;

public class LabelConfig {
    private Integer id;

    private Integer lid;

    private String firstLevelName;

    private String secondLevelName;

    private String secondLevelField;

    private String thirdLevelName;

    private String firstLevelId;

    private String secondLevelId;

    private String thirdLevelId;

    private String hdfsPath;

    private String hiveTable;

    private String hbaseTable;

    private String hbaseColumn;

    private String mergeHbaseTable;

    private String mergeHbaseColumn;

    private Date createTime;

    private String type;

    private Integer availably;

    private Integer mutex;

    private Integer period;

    private Double alpha;

    private Long labelNum;

    private Date updateDate;

    private Integer isNum;

    private Integer isCountLabel;

    private String hiveField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public String getFirstLevelName() {
        return firstLevelName;
    }

    public void setFirstLevelName(String firstLevelName) {
        this.firstLevelName = firstLevelName == null ? null : firstLevelName.trim();
    }

    public String getSecondLevelName() {
        return secondLevelName;
    }

    public void setSecondLevelName(String secondLevelName) {
        this.secondLevelName = secondLevelName == null ? null : secondLevelName.trim();
    }

    public String getSecondLevelField() {
        return secondLevelField;
    }

    public void setSecondLevelField(String secondLevelField) {
        this.secondLevelField = secondLevelField == null ? null : secondLevelField.trim();
    }

    public String getThirdLevelName() {
        return thirdLevelName;
    }

    public void setThirdLevelName(String thirdLevelName) {
        this.thirdLevelName = thirdLevelName == null ? null : thirdLevelName.trim();
    }

    public String getFirstLevelId() {
        return firstLevelId;
    }

    public void setFirstLevelId(String firstLevelId) {
        this.firstLevelId = firstLevelId == null ? null : firstLevelId.trim();
    }

    public String getSecondLevelId() {
        return secondLevelId;
    }

    public void setSecondLevelId(String secondLevelId) {
        this.secondLevelId = secondLevelId == null ? null : secondLevelId.trim();
    }

    public String getThirdLevelId() {
        return thirdLevelId;
    }

    public void setThirdLevelId(String thirdLevelId) {
        this.thirdLevelId = thirdLevelId == null ? null : thirdLevelId.trim();
    }

    public String getHdfsPath() {
        return hdfsPath;
    }

    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath == null ? null : hdfsPath.trim();
    }

    public String getHiveTable() {
        return hiveTable;
    }

    public void setHiveTable(String hiveTable) {
        this.hiveTable = hiveTable == null ? null : hiveTable.trim();
    }

    public String getHbaseTable() {
        return hbaseTable;
    }

    public void setHbaseTable(String hbaseTable) {
        this.hbaseTable = hbaseTable == null ? null : hbaseTable.trim();
    }

    public String getHbaseColumn() {
        return hbaseColumn;
    }

    public void setHbaseColumn(String hbaseColumn) {
        this.hbaseColumn = hbaseColumn == null ? null : hbaseColumn.trim();
    }

    public String getMergeHbaseTable() {
        return mergeHbaseTable;
    }

    public void setMergeHbaseTable(String mergeHbaseTable) {
        this.mergeHbaseTable = mergeHbaseTable == null ? null : mergeHbaseTable.trim();
    }

    public String getMergeHbaseColumn() {
        return mergeHbaseColumn;
    }

    public void setMergeHbaseColumn(String mergeHbaseColumn) {
        this.mergeHbaseColumn = mergeHbaseColumn == null ? null : mergeHbaseColumn.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getAvailably() {
        return availably;
    }

    public void setAvailably(Integer availably) {
        this.availably = availably;
    }

    public Integer getMutex() {
        return mutex;
    }

    public void setMutex(Integer mutex) {
        this.mutex = mutex;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Long getLabelNum() {
        return labelNum;
    }

    public void setLabelNum(Long labelNum) {
        this.labelNum = labelNum;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIsNum() {
        return isNum;
    }

    public void setIsNum(Integer isNum) {
        this.isNum = isNum;
    }

    public Integer getIsCountLabel() {
        return isCountLabel;
    }

    public void setIsCountLabel(Integer isCountLabel) {
        this.isCountLabel = isCountLabel;
    }

    public String getHiveField() {
        return hiveField;
    }

    public void setHiveField(String hiveField) {
        this.hiveField = hiveField == null ? null : hiveField.trim();
    }
}