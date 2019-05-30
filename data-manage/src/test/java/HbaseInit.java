import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.bfd.api.repository.HbaseRepository;
import com.bfd.api.utils.Constants;

public class HbaseInit {
	
	/**
	 * 写入数据
	 * @param table
	 * @param rowKey
	 * @param columnValues
	 * @throws Exception
	 */
	public static void insertOne(String tableName,String rowKey,Map<String,Object> columnValues){
	    Table table = HbaseRepository.getTable(tableName);
		try{
	        Put put = new Put(Bytes.toBytes(rowKey));
	        if (columnValues != null && columnValues.size() > 0) {
	        	Set<Entry<String, Object>> entrySet = columnValues.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> next = iterator.next();
					String key = next.getKey(); // 字段名称
					Object value = next.getValue(); // 字段值
					if (value == null || "".equals(value)) {
						continue;
					}
					if (value instanceof byte[]) {
						put.addColumn(Bytes.toBytes("up"), Bytes.toBytes(key), (byte[]) value);
					} else {
						put.addColumn(Bytes.toBytes("up"), Bytes.toBytes(key), Bytes.toBytes(value.toString()));
					}
				}
				table.put(put);
	        }
	    }catch(Exception e){
			e.printStackTrace();
	    }finally {
            if (table != null) {
                try {
                	table.close();
                } catch (IOException e) {
                }
            }
        }
	}

	public static void main(String[] args) {
		
		String idstr = "000b28a9-c62d-4e50-84b5-3c7974a4f7b5,0007b628-10cc-49a5-9025-bbec7c52cb03,001f4abd-76c7-4956-9589-804c223124df";
		String[] ids = idstr.split(",");
		String mostr = "18501268917,18501268927,18501268937";
		String[] mos = mostr.split(",");
		for(int i=0;i<ids.length;i++){
			String id = ids[i];
			String pks = "global:superid:"+id;
			String rowKey = HbaseRepository.getRowkey(pks);
			Map<String,Object> columnValues = new HashMap<String,Object>();
			columnValues.put("ids:global:mobile", mos[i]);
			System.out.println(pks);
			insertOne(Constants.HBASE_USER, rowKey, columnValues);
		}

	}
}
