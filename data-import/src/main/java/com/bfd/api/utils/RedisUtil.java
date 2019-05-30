package com.bfd.api.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wandoulabs.jodis.BfdJodis;

import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static BfdJodis bfdjodis;

	// 禁止实例化
	private RedisUtil() {
	}

	// 初始化连接
	static {
		bfdjodis=new BfdJodis(Constants.ZK_HOST, 50000, 
				Constants.REDIS_PROXY_PATH,new JedisPoolConfig(), 
				5000, Constants.REDIS_BUSINESS_ID);
	}

	// 存入
	public static boolean set(String key, String value) {
		try {
			String result = bfdjodis.set(key, value);
			return "OK".equals(result) ? true : false;
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  value=" + value + "  redis赋值异常", e);
		}
		return false;
	}

	// 取值
	public static String get(String key) {
		return bfdjodis.get(key);

	}
	
	public static Integer getInt(String key){
		if(StringUtils.isEmpty(bfdjodis.get(key))){
			return 0;
		}
		return Integer.parseInt(bfdjodis.get(key));
	}

	// 设置过期时间
	public static void expire(String key, int seconds) {
		bfdjodis.expire(key, seconds);
	}

	// 是否存在Key
	public static boolean exist(String key) {
		try {
			return bfdjodis.exists(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis判断存在异常", e);
			return false;
		}
	}

	public static boolean hexist(String key, String field) {
		try {
			return bfdjodis.hexists(key, field);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  field=" + field + "  redis hexist 异常", e);
			return false;
		}
	}

	public static Long hset(String key, String field, String value) {
		try {
			return bfdjodis.hset(key, field, value);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  field=" + field + "  value=" + value + "redis hset 异常", e);
			return 0L;
		}
	}

	public static String hget(String key, String field) {
		return bfdjodis.hget(key, field);
	}

	public static Map<String, String> hgetAll(String key) {
		return bfdjodis.hgetAll(key);
	}

	public static Long del(String key) {
		try {
			return bfdjodis.del(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis del 异常", e);
			return 0L;
		}
	}

	public static Long hdel(String key, String fields) {
		try {
			return bfdjodis.hdel(key, fields);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  fileds=" + fields + "redis异常", e);
			return 0L;
		}
	}

	// 计数器
	public static Long incr(String key) {
		try {
			return bfdjodis.incr(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis incr 异常", e);
			return 0L;
		}
	}

	// 缓存List{vlaue}
	public static boolean lpush(String key, String value) {
		try {
			Long result = bfdjodis.lpush(key, value);
			return result > 0 ? true : false;
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + "  value=" + value + "  redis lpush 异常", e);
		}
		return false;
	}

	public static String lpop(String key) {
		return bfdjodis.lpop(key);
	}

	public static List<String> lrange(String key, long start, long end) {
		return bfdjodis.lrange(key, start, end);
	}

	public static long llen(String key) {
		return bfdjodis.llen(key);
	}

	public static Long lrem(String key, long count, String value) {
		return bfdjodis.lrem(key, count, value);
	}

}