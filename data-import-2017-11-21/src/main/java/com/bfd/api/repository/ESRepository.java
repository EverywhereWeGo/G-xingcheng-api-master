package com.bfd.api.repository;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.bfd.api.utils.ESUtils;
import com.bfd.api.utils.LogUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ESRepository {

	/**
	 * 插入es
	 * 
	 * @param indexName
	 * @param typeName
	 * @param json
	 */
	public static boolean insert(String indexName, String typeName, String _id, Map<String, Object> json) {
		IndexResponse response = ESUtils.getClient().prepareIndex(indexName, typeName).setId(_id).setSource(json).execute().actionGet();
		return response.isCreated();
	}

	public static Map<String, Object> queryId(String indexName, BoolQueryBuilder queryBuilder, int from, int size) {
		
		Map<String, Object> result = Maps.newHashMap();

		SearchRequestBuilder requestBuilder = ESUtils.getClient().prepareSearch(indexName.toLowerCase());
		requestBuilder.setQuery(queryBuilder);

		if (from < 0) {
			from = 0;
		} else {
			from = from - 1;
		}
		requestBuilder.setFrom(from).setSize(size);
		LogUtils.debug(requestBuilder.toString());
		SearchResponse searchResponse = requestBuilder.execute().actionGet();
		SearchHits searchHits = searchResponse.getHits();
		if (searchHits == null || searchHits.getTotalHits() == 0) {
			result.put("totalSize", 0);
			return result;
		}

		result.put("totalSize", searchHits.getTotalHits());
		SearchHit[] array = searchHits.getHits();
		List<String> list = Lists.newArrayList();
		for (SearchHit hit : array) {
			list.add(hit.getId());
		}
		result.put("data", list);
		return result;
	}
	
	public static long getSum(String indexName, BoolQueryBuilder queryBuilder) {
		SearchRequestBuilder requestBuilder = ESUtils.getClient().prepareSearch(indexName.toLowerCase());
		requestBuilder.setQuery(queryBuilder);
		SearchResponse searchResponse = requestBuilder.execute().actionGet();
		SearchHits searchHits = searchResponse.getHits();
		if (searchHits == null || searchHits.getTotalHits() == 0) {
			return 0;
		}
		return searchHits.getTotalHits();
	}
}

