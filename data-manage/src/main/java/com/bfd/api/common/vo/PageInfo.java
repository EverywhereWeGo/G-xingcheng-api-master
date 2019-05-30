package com.bfd.api.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.bfd.api.vo.UserInfo_SMS;

public class PageInfo<T> {
    private List<T> totalList;	   		//集合
    private Integer totalSize=0;		   //总行数
    private Integer pageSize=0;       //每页数目 默认15
    private Integer currentPage=0;  	   //当前页
    private Integer pageNo = 0;
    private Integer pg = 0;
    public PageInfo(){}
    public PageInfo(Integer currentPage,Integer pageSize){
        if(currentPage==null){
            currentPage=1;
        }
        this.pageNo=(currentPage-1)*pageSize;
        this.pageSize=pageSize;
        this.pg = pageSize;
        this.currentPage=currentPage;
    }
    
    public PageInfo(Integer currentPage,Integer pageSize,Integer totalSize){
        if(currentPage==null){
            currentPage=1;
        }
        this.pageNo=(currentPage-1)*pageSize;
        this.pageSize=pageSize;
        this.currentPage=currentPage;
        this.totalSize = totalSize;
        this.pg = pageSize;
    }
    
    public PageInfo(Integer currentPage,Integer pageSize,Integer totalSize,Integer status){
        if(currentPage==null){
            currentPage=1;
        }
        
        if(status==0)
        	{this.pageSize=pageSize;
            this.pageNo=totalSize-pageSize;
            if(totalSize-pageSize==0)
            this.pg=totalSize;
            else{
            	this.pg=(totalSize-pageSize)/(currentPage-1);
            }
        	}
        if(status==1)
        {this.pageSize=pageSize;
        this.pageNo=(currentPage-1)*pageSize;
        this.pg = pageSize;
        }
        this.currentPage=currentPage;
        this.totalSize = totalSize;
    }

    public Integer getTotalPage() {
        if (pg==null||pg == 0) {
            pageSize = 0;
            pg=1;
        }
        if (this.totalSize % this.pg == 0) {
            return (this.totalSize / this.pg);
        }
        return (this.totalSize / this.pg + 1);
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
            pageSize = 0;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.pg = pageSize;
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
	public void setTotalList(List<UserInfo_SMS> list,int a){
        this.totalList = (List<T>) list;
	}
}
