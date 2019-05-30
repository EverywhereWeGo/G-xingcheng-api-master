package com.bfd.api.zuul.utils;


import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import io.netty.util.internal.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description:redis工具类
 * @author bo.xu
 * 2015年7月21日 下午3:07:47
 */
public class RedisUtils {
	private static final Log logger= LogFactory.getLog(RedisUtils.class);
	
	private static JedisPool jedisPool = null;
	
	static{
		if(jedisPool==null){
			JedisPoolConfig config = new JedisPoolConfig();  
//			config.setMaxTotal(new Integer(ConfigUtil.getInstance().get("log.redis.maxActive")));
//			config.setMaxWaitMillis(new Long(ConfigUtil.getInstance().get("log.redis.maxWait")));
//			config.setMaxIdle(new Integer(ConfigUtil.getInstance().get("log.redis.maxIdle")));
//			config.setMinIdle(new Integer(ConfigUtil.getInstance().get("log.redis.minIdle")));
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, ConfigUtil.getInstance().get("log.redis.host"), new Integer(ConfigUtil.getInstance().get("log.redis.port"))); ;
		}
	}
	
	public static void returnResource(Jedis jedis,boolean isBroken){
	    if (jedis == null)
	        return;
	    
		if(isBroken){
			jedisPool.returnBrokenResource(jedis);
		}else{
			jedisPool.returnResource(jedis);
		}
	}
	
	public static boolean set(String key,String value){
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			String res = jedisClient.set(key, value);
			return "OK".equalsIgnoreCase(res);
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
		}finally{
			returnResource(jedisClient, isBroken);
		}
		return false;
	}
	
	public static String get(String key){
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			return jedisClient.get(key);
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
		}finally{
			returnResource(jedisClient, isBroken);
		}
		return null;
	}
	
	public static void delKey(String key){
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			jedisClient.del(key);
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	
	//清空当前数据库所有key
	public static void cleanDB() throws Exception {
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			jedisClient.flushDB();
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
			throw e;
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	
	//清空当所有数据库所有key
	public static void cleanAll() throws Exception {
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			jedisClient.flushAll();
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
			throw e;
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	
	//得到Jedis对象可以处理没有封装的方法
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	//设置过期key
	public static void setExpireKey(String key,String value,int seconds) throws Exception {
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = getJedis();
			jedisClient.set(key, value);
			jedisClient.expire(key, seconds);
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
			throw e;
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	
	public static void setExpireTime(String key,int seconds) throws Exception {
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = getJedis();
			jedisClient.expire(key, seconds);
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
			throw e;
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	
	/**
	 * Description:添加或更新更新redis公用方法(key ，value转成json)
	 * @Version1.0 2015年7月21日 下午11:11:00 by bo.xu创建
	 * @param webApplicationContext
	 */
	public static void saveOrUpdateRdeis(String key,Object object) throws Exception {
		set(key,JSON.toJSONString(object));
	}	
	
	/**
	 * Description:添加或更新更新redis公用方法(key值mad5加密 ，value转成json)
	 * @Version1.0 2015年7月21日 下午11:11:00 by bo.xu创建
	 * @param webApplicationContext
	 */
//	public static void setForMd5(String key,Object object){
//		set(StringUtil.stringMD5(key),JSON.toJSONString(object));
//	}
//	
//	public static String getForMd5(String key) {
//		return get(StringUtil.stringMD5(key));
//	}
//	
//	public static void delKeyForMd5(String key){
//		delKey(StringUtil.stringMD5(key));
//	}
	/**
	 * 批量删除key
	 * @param prefix
	 */
	public static void batchDel(String prefix){
		boolean isBroken = false;
		Jedis jedisClient = null;
		try{
			jedisClient = jedisPool.getResource();
			Set<String> set = jedisClient.keys(prefix +"*");  
	        Iterator<String> it = set.iterator();  
	        while(it.hasNext()){  
	            String keyStr = it.next();  
	            jedisClient.del(keyStr);
	        }
		}catch(Exception e){
			e.printStackTrace();
			isBroken = true;
			logger.error(e);
		}finally{
			returnResource(jedisClient, isBroken);
		}
	}
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	public static void main(String[] args) {
		try {
			for(int i=0;i<3000;i++){
				final int m = i;
				threadPool.execute(
					new Runnable(){
						public void run() {
							try {
								RedisUtils.set("a"+m,"n"+m);
								String value = RedisUtils.get("a"+m);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
