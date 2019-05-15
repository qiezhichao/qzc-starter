package com.qzc.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.TimeUnit;

/**
 *  基于Guava的本地缓存
 * @Author:         qiezhichao
 * @CreateDate:     2019/5/12 14:15
 */
public class LocalCache {

    // CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
    private static Cache<String, String> loadingCache = CacheBuilder.newBuilder()
            // 设置缓存容器的初始容量为50
            .initialCapacity(50)
            // 设置缓存最大容量为500，超过500之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(500)
            // 设置要统计缓存的命中率
            .recordStats()
            // 设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            // 设置写缓存后120秒钟过期
            .expireAfterAccess(120, TimeUnit.SECONDS)
            // 设置缓存的移除通知
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    System.out.println(notification.getKey() + " 被移除, 移除原因： " + notification.getCause());
                }
            })
            .build();

    public static void setKeyVal(String key, String value) {
        loadingCache.put(key, value);
    }

    public static String getValByKey(String key) {
        return loadingCache.getIfPresent(key);
    }

    public static void delKey(String key) {
        loadingCache.invalidate(key);
    }
}
