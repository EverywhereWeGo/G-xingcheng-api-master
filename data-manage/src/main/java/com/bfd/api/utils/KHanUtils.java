package com.bfd.api.utils;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khandb.copy.CopyManager;
import com.khandb.core.BaseConnection;

/**
 * KHAN工具类
 *
 */
public class KHanUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(KHanUtils.class);
	
    private Connection conn;

    public KHanUtils() {
        init();
    }
    
    public boolean init(){
    	if(conn != null){
    		try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage());
			}
    		conn = null;
    	}

        try{  
            Class.forName(Constants.KHAN_DRIVER);
            conn = DriverManager.getConnection(Constants.KHAN_URL, Constants.KHAN_USERNAME, Constants.KHAN_PASSWORD);  
            logger.info("数据库连接成功！");
        }catch(Exception e) {
            logger.error(null, e);
            return false;
        }
        return true;
    }


    public Connection getConn() {
        return conn;
    }

    /**
     * copy hbase export data to khan
      * @param sql
     * @return
     */
    public long copyDataToKhan(String sql,String dataPath ) {
        logger.debug(sql);
        if(null == conn && !init()){
            return -1;
        }

        try {
            FileReader fileReader = new FileReader(dataPath);
            CopyManager copyManager = new CopyManager((BaseConnection) conn);
            return copyManager.copyIn(sql, fileReader );
        } catch (Exception e) {
            logger.error(null, e);
            return -1;
        }
    }



    /**
     * get all column type
     * @param
     * @return
     */
    public Map getAllColumnType(String tableName ) {
        String sql = "select * from " + tableName + " limit 1";
        Map<String, String> ColumnMapType = new HashMap<String, String>();
        logger.debug(sql);
        if (null == conn && !init()) {
            return ColumnMapType;
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSetMetaData rsm = pstmt.getMetaData();
            int colnum = rsm.getColumnCount();
            for (int i = 1; i <= colnum; i++) {
                ColumnMapType.put(rsm.getColumnName(i).toLowerCase(), rsm.getColumnTypeName(i));
            }
            pstmt.close();
            return ColumnMapType;
        } catch (Exception e) {
            logger.error(null, e);
            return ColumnMapType;
        }
    }


    /**
     * 插入、更新操作
     * @param sql
     * @return
     */
    public int executeUpdate(String sql) {
        logger.debug(sql);
        if (null == conn && !init()) {
            return -1;
        }

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            return stmt.executeUpdate();
        } catch (Exception e) {
            logger.error(null, e);
            init();
            return -1;
        } finally {
            close(stmt, null);
        }
    }

    public List<String> getNotnullColumns(String tableName) {
        String sqlfmt = "select attname from pg_attribute where attrelid = ("
                + " select oid from pg_class where relname = '%s' and relnamespace = ("
                    + " select oid from pg_namespace where nspname = '%s'))"
                + " and attnum > 0 and attnotnull = true";
        String[] names = tableName.split("\\.");
        String tabName, schemaName;
        if (names.length == 1) {
            tabName = names[0].toLowerCase();
            schemaName = "public";
        } else {
            tabName = names[1].toLowerCase();
            schemaName = names[0].toLowerCase();
        }

        String sql = String.format(sqlfmt, tabName, schemaName);
        Statement stmt = null;
        List<String> res = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                res = new ArrayList<>();
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
                rs.close();
            }
        } catch (SQLException e) {
            logger.error(null, e);
            res = null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(null, e);
                }
            }
        }
        return res;
    }

    /**
     * 进行数据库连接的关闭
     * */
    public void close(PreparedStatement preparedStatement,ResultSet resultSet){
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                logger.error(null, e);
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(null, e);
            }
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(null, e);
            }
        }
    }
    
}
