package com.bfd.api.utils;
import java.util.HashMap;
import java.util.Map;
public class ConstantsUtil
{
  public static final String HBASE_USER_INFO_TABLE_NAME = ConfigUtil.getInstance().get("bfd.data.hbase.table");
  public static final String USER_CHANNEL_HBASECOL = ConfigUtil.getInstance().get("bfd.rest.user.channel.hbasecolumn");
  public static final String HBASE_USER_INFO_TABLE_COLUMN_FAMILY = "ids";
  public static final String HBASE_V3_TABLE_COLUMN_FAMILY = "up";
  public static final String HBASE_USER_INFO_TABLE_ROWKEY_PREFIX = "global";
  public static final String HBASE_USER_INFO_TABLE_OUT_ROWKEY_PREFIX = "global";
  public static final String HBASE_V3_TABLE_NAME = ConfigUtil.getInstance().get("bfd.rest.hbase.all.data.table");
  public static final String HBASE_V3_7_TABLE_NAME = "OfflineUserProfileV3_7";
  public static final String HBASE_V3_30_TABLE_NAME = "OfflineUserProfileV3_30";
  public static final Map<String, String> BQ0006_SEARCH_CONDITION_COLS_MAPPER = new HashMap<String, String>();
  public static final Map<String, String> BQ0011_RELATION_HBASE_MAPPER = new HashMap<String, String>();
}
