package com.bfd.api.vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class QueryTerm {

	private String tagLogo;
	
	private String tagProject;
	
	private String grouping;

	public String getTagLogo() {
		return tagLogo;
	}

	public void setTagLogo(String tagLogo) {
		this.tagLogo = tagLogo;
	}

	public String getTagProject() {
		return tagProject;
	}

	public void setTagProject(String tagProject) {
		this.tagProject = tagProject;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}
	
	public static void main(String[] args) {
		List<QueryTerm> query = new ArrayList<QueryTerm>();
		QueryTerm q = new QueryTerm();
		q.setGrouping("1000010000100001");
		q.setTagLogo("my");
		q.setTagProject("pj101");
		query.add(q);
		String str = JSONArray.toJSONString(query);
		System.out.println(str);
	}
}
