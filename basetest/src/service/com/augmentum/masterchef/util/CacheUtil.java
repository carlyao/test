//package com.augmentum.masterchef.util;
//
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Array;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//
//import com.kt.cache.TimeToLive;
//import com.kt.cache.local.service.LocalCache;
//import com.kt.cache.local.service.LocalCacheService;
//import com.kt.cache.remote.service.RemoteCache;
//import com.kt.cache.remote.service.RemoteCacheService;
//
//public class CacheUtil {
//	
//	private static final String CACHE_ENABLED_FOR_TESTING = "cacheEnabledForTesting";
//	private static boolean cacheEnabledForTesting = InitProps.getInstance().getBoolean(CACHE_ENABLED_FOR_TESTING);
//	
//	private static final String CACHE_ENABLED_FOR_TESTING_TIME = "cacheEnabledForTestingTime";
//	private static int CACHE_TIME = InitProps.getInstance().getInt(CACHE_ENABLED_FOR_TESTING_TIME);
//
//    /**
//     * Get default remote cache
//     * @return
//     */
//	public static RemoteCache getRemoteCache() {
//		if (InitProps.getInstance().isRemoteCacheEnabled()) {
//			return RemoteCacheService.getCache();
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * Get default local cache
//	 * @return
//	 */
//	public static LocalCache getLocalCache() {
//		if (InitProps.getInstance().isLocalCacheEnabled()) {
//			return LocalCacheService.getCache();
//		} else {
//			return null;
//		}
//	}
//	
//	/**
//	 * Load object from local cache
//	 * @param key
//	 * @return
//	 */
//	public static Object loadFromLocalCache(Object key){
//		LocalCache cache=getLocalCache();
//		if(cache!=null){
//			return cache.get(key);
//		}
//		return null;
//	}
//
//	
//	/**
//	 * Load object from remote cache
//	 * @param key
//	 * @return
//	 */
//	public static Object loadFromRemoteCache(String key){
//		RemoteCache cache=getRemoteCache();
//		if(cache!=null){
//			return cache.get(key);
//		}
//		return null;
//	}
//	
//	/**
//	 * Store object to local cache
//	 * @param key
//	 * @param value
//	 */
//	public static void saveToLocalCache(Object key, Object value){
//		LocalCache cache=getLocalCache();
//		if(cache!=null){
//            preloadEntity(value);
//            
//            // For testing
//            if (cacheEnabledForTesting){
//            	cache.put(key, value, CACHE_TIME);
//            } else {
//            	cache.put(key, value);
//            }
//		}
//	}
//	
//	/**
//	 * Store object to remote cache
//	 * @param key
//	 * @param value
//	 */
//	public static void saveToRemoteCache(String key, Object value){
//		RemoteCache cache=getRemoteCache();
//		if(cache!=null){
//            preloadEntity(value);
//            
//            // For testing
//            if (cacheEnabledForTesting){
//            	saveToRemoteCache(key, value, CACHE_TIME);
//            } else {
//            	cache.put(key, value);
//            }
//		}
//	}
//	
//	/**
//	 * 
//     * Store object to local cache
//     * @param key
//     * @param value
//     * @param seconds
//     */
//    private static void saveToLocalCache(Object key, Object value, int seconds){
//    	// For testing
//    	if (cacheEnabledForTesting){
//    		seconds = CACHE_TIME;
//    	}
//    	
//        LocalCache cache=getLocalCache();
//        if(cache!=null){
//            preloadEntity(value);
//            cache.put(key, value, seconds);
//        }
//    }
//    
//    /**
//     * 
//     * Store object to remote cache
//     * @param key
//     * @param value
//     * @param seconds -1 unlimited
//     */
//    private static void saveToRemoteCache(String key, Object value, int seconds) {
//    	// For testing
//    	if (cacheEnabledForTesting){
//    		seconds = CACHE_TIME;
//    	}
//    	
//        RemoteCache cache = getRemoteCache();
//        if (cache != null) {
//            
//            preloadEntity(value);
//            
//            if (seconds > 0) {
//                Date expDate = new Date(System.currentTimeMillis() + seconds
//                        * 1000);
//            }
//            Date expDate = null;
//            if (seconds > 0) {
//                expDate = new Date(System.currentTimeMillis() + seconds * 1000);
//            } else if (seconds < 0) {
//                expDate = new Date(0);
//            }
//            if (expDate != null) {
//                cache.put(key, value, expDate);
//            } else {
//                cache.put(key, value);
//            }
//        }
//    }
//    
//
//    
//    /**
//     * Store object to local cache
//     * @param key
//     * @param value
//     * @param seconds
//     */
//    public static void saveToLocalCache(Object key, Object value, TimeToLive timeToLive){
//        saveToLocalCache(key, value, timeToLive.getDuration());
//    }
//    
//    /**
//     * Store object to remote cache
//     * @param key
//     * @param value
//     * @param seconds -1 unlimited
//     */
//    public static void saveToRemoteCache(String key, Object value, TimeToLive timeToLive) {
//        saveToRemoteCache(key, value, timeToLive.getDuration());
//    }
//	
//	/**
//	 * Remove object from local cache
//	 * @param key
//	 */
//	public static void removeFromLocalCache(String key){
//	    LocalCache cache=getLocalCache();
//        if(cache!=null && cache.exists(key)){
//            cache.remove(key);
//        }
//	}
//	
//
//    /**
//     * Remove object from remote cache
//     * @param key
//     */
//    public static void removeFromRemoteCache(String key){
//        RemoteCache cache=getRemoteCache();
//        if(cache!=null && cache.exists(key)){
//            cache.remove(key);
//        }
//    }
//    
//    /**
//     * Visit all sub entities for given entity to avoid the lazy load issue out
//     * of session
//     * 
//     * @param entity
//     */
//    public static void preloadEntity(Object entity) {
//        if (entity != null) {
//            // Handle collection first
//            if (entity instanceof Collection) {
//                preloadCollection((Collection) entity);
//            } else if (entity instanceof Array) {
//                preloadArray((Array) entity);
//            } else if (entity instanceof Map) {
//                preloadMap((Map) entity);
//            } else {
//                // Single bean
//                BeanWrapper entityWrapper = new BeanWrapperImpl(entity);
//                PropertyDescriptor[] descriptors = entityWrapper
//                        .getPropertyDescriptors();
//                // Handle each property
//                for (PropertyDescriptor descriptor : descriptors) {
//                    // If the property is collection, then visit it
//                    if (descriptor.getReadMethod() != null) {
//                        if (Collection.class.isAssignableFrom(descriptor
//                                .getPropertyType())) {
//                            preloadCollection((Collection) entityWrapper
//                                    .getPropertyValue(descriptor.getName()));
//                        } else if (Array.class.isAssignableFrom(descriptor
//                                .getPropertyType())) {
//                            preloadArray((Array) entityWrapper
//                                    .getPropertyValue(descriptor.getName()));
//                        } else if (Map.class.isAssignableFrom(descriptor
//                                .getPropertyType())) {
//                            preloadMap((Map) entityWrapper
//                                    .getPropertyValue(descriptor.getName()));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Visit all entities for given collection to avoid the lazy load issue out
//     * of session
//     * 
//     * @param entity
//     */
//    private static void preloadCollection(Collection entities) {
//        if (entities != null) {
//            for (Object entity : entities) {
//                preloadEntity(entity);
//            }
//        }
//    }
//
//    /**
//     * Visit all entities for given array to avoid the lazy load issue out
//     * of session
//     * 
//     * @param entity
//     */
//    private static void preloadArray(Array entities) {
//        if (entities != null) {
//            int length = Array.getLength(entities);
//            for (int i = 0; i < length; i++) {
//                preloadEntity(Array.get(entities, i));
//            }
//        }
//    }
//    
//    /**
//     * Visit all entities for given map to avoid the lazy load issue out
//     * of session
//     * 
//     * @param entity
//     */
//    private static void preloadMap(Map entities) {
//        if (entities != null) {
//            preloadCollection(entities.values());
//        }
//    }
//}
