package com.bfd.api.zuul.utils;

import java.util.List;
import java.util.Map;

import com.wandoulabs.jodis.BfdJodis;
import com.wandoulabs.jodis.RoundRobinJedisPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static BfdJodis bfdjodis;
	private static RoundRobinJedisPool bfdjodis1;
	// 禁止实例化
	private RedisUtil() {
	}
    
	// 初始化连接
	static {
		bfdjodis = new BfdJodis(ConfigUtil.getInstance().get("ds.redis.zk"), 50000, ConfigUtil.getInstance().get("ds.redis.proxyPath"), new JedisPoolConfig(), 5000, ConfigUtil.getInstance().get("ds.redis.businessid"));
	}
	
	static {
		
		bfdjodis1 = new RoundRobinJedisPool(ConfigUtil.getInstance().get("ds.redis.zk"), 50000, ConfigUtil.getInstance().get("ds.redis.proxyPath"), new JedisPoolConfig(), 5000);
	}
	
	public static String flushall(){
		Jedis a = bfdjodis1.getResource();
		String result = a.flushAll();
		a.close();
		return result;
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
		String result = null;
		try {
			result = bfdjodis.get(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  redis取值异常", e);
		}
		return result;
	}

	// 设置过期时间
	public static void expire(String key, int seconds) {
		try {
			bfdjodis.expire(key, seconds);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error("key=" + key + "  redis设置过期时间异常", e);
		}
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
		try {
			return bfdjodis.hget(key, field);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis hget 异常", e);
			return null;
		}

	}

	public static Map<String, String> hgetAll(String key) {
		try {
			return bfdjodis.hgetAll(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis hgetAll 异常", e);
			return null;
		}
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
		try {
			return bfdjodis.lpop(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis lpop 异常", e);
			return null;
		}
	}

	public static List<String> lrange(String key, long start, long end) {
		try {
			return bfdjodis.lrange(key, start, end);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis lrange 异常", e);
			return null;
		}
	}
	
	
	public static long llen(String key) {
		try {
			return bfdjodis.llen(key);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis llen 异常", e);
			return 0L;
		}
	}

	public static Long lrem(String key, long count, String value) {
		try {
			return bfdjodis.lrem(key, count, value);
		} catch (Exception e) {
			LogUtil.getLogger(LogType.Run).error(key + " redis lrem 异常", e);
			return 0L;
		}
	}

}