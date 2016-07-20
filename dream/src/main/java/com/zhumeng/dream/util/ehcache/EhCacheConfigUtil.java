package com.zhumeng.dream.util.ehcache;

import com.zhumeng.dream.util.spring.SpringContextHolder;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存工具，手动操作缓存对象
 * 
 * @author chengkunxf
 *
 */
public class EhCacheConfigUtil {
	
	public static final String GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME = "ehCacheManager";// GeneralCacheAdministrator注入Bean名称
	
	/**
	 * 应用全局配置缓存KEY
	 */
	private static final String CACHE_NAME = "setting";
	
	private static Cache cache;
	
	private static CacheManager cacheManager;
	
	/**
	 * 单列模式,得到缓存管理对象实例
	*/ 
	private static CacheManager getCacheManagerInstance() {
		if(cacheManager == null)
			cacheManager = (CacheManager) SpringContextHolder.getBean(GENERAL_CACHE_ADMINISTRATOR_BEAN_NAME);
		return cacheManager;
	}
	
	/**
	 * 根据Key读取缓存
	 * 
	 * @return 缓存对象
	 */
	public static Object getFromCache(String key) {
		cache = getCacheManagerInstance().getCache(CACHE_NAME);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}
	
	/**
	 * 加入对象到缓存
	 * 
	 */
	public static void putInCache(String key, Object object) {
		Element element = new Element(key, object);
		cache = getCacheManagerInstance().getCache(CACHE_NAME);
		cache.put(element);
	}
	
	/**
	 * 刷新全局缓存对象
	 * 
	 */
	public static void flush() {
		getCacheManagerInstance().getCache(CACHE_NAME).flush();
	}
	
	/**
	 * 清除所有缓存
	*/ 
	public static void clearAll() {
		getCacheManagerInstance().clearAll();
	}
}
