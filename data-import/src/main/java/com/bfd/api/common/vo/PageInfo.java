package com.bfd.api.common.vo;

import java.util.ArrayList;
import java.util.List;

public class PageInfo<T> {
    private List<T> totalList;	   		//集合
    private Integer totalSize=0;		   //总行数
    private Integer pageSize=15;       //每页数目 默认15
    private Integer currentPage=0;  	   //当前页
    private Integer pageNo = 0;

    public PageInfo(){}
    public PageInfo(Integer currentPage,Integer pageSize){
        if(currentPage==null){
            currentPage=1;
        }
        this.pageNo=(currentPage-1)*pageSize;
        this.pageSize=pageSize;
        this.currentPage=currentPage;
    }

    public Integer getTotalPage() {
        if (pageSize==null||pageSize == 0) {
            pageSize = 15;
        }
        if (this.totalSize % this.pageSize == 0) {
            return (this.totalSize / this.pageSize);
        }
        return (this.totalSize / this.pageSize + 1);
    }

    public List<T> getTotalList() {
    	if(totalList==null || totalList.size()==0){
    		return (new ArrayList<T>());
    	}
        return totalList;
    }

    public void setTotalList(List<T> lists) {
        this.totalList = lists;
    }

    public Integer getTotalSize() {
        if (totalSize==null) {
            totalSize = 0;
        }
        return totalSize;
    }

    public void setTotalSize(Integer totalCount) {
        this.totalSize = totalCount;
    }

    public Integer getPageSize() {
        if (pageSize==null||pageSize == 0) {
            pageSize = 15;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentPage() {
        if (currentPage==null||currentPage <= 0) {
            this.currentPage = 1;
        }
        return this.currentPage;
    }
    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
