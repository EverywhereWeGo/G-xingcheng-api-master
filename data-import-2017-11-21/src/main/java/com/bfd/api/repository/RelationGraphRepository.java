package com.bfd.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;

import com.alibaba.fastjson.JSONObject;
import com.bfd.api.utils.Constants;

public class RelationGraphRepository extends HbaseRepository {

	/**
	 * 根据手机号批量查询
	 * @param superids
	 * @return
	 */
	public static List<String> bulkGet(List<String> superids) throws Exception {
		Table table = null;
		try {
			table = getTable(Constants.HBASE_RELATION_GRAPH);
			if(table != null) {
				List<Get> list = new ArrayList<Get>();
				for(String id:superids){
					String pks = "global:superid:"+id;
					String key = getRowkey(pks);
					Get get = new Get(key.getBytes()); //根据主键查询
					list.add(get);
				}
				
				Result[] rs = table.get(list);
				List<String> idlist = new ArrayList<String>();
				if(rs != null && rs.length>0) {
					for(Result r:rs){
						Cell[] cells = r.rawCells();
						for(Cell cell:cells){
							String key = new String(CellUtil.cloneQualifier(cell));
							if(StringUtils.equals("global:mobile", key)){
								String str = new String(CellUtil.cloneValue(cell));
								Map<String,String> bean = JSONObject.parseObject(str, Map.class);
								for(Entry<String, String> entry:bean.entrySet()){
									idlist.add(entry.getKey());
								}
							}
						}
					}
					return idlist;
				}
			}
		} finally {
			if (table != null) {
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
