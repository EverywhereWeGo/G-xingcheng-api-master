package com.bfd.api.common.vo;

public class Page {
	public Page() {
	}
    public Page(int pageNo,int pageSize){
        if(pageSize==0){
            pageSize=15;
        }
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }
    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPage;
    private Integer needPage;

    public Integer getNeedPage() {
		return needPage;
	}
	public void setNeedPage(Integer needPage) {
		this.needPage = needPage;
	}
	public int getStart() {
//        return start;
		  if(pageSize==0){
	            pageSize=15;
	        }
		  if(pageNo<1){
			  pageNo=1;
		  }
	    return (pageNo-1)*pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.totalPage = (total+pageSize)/pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
