package com.qzc.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.qzc.annotation.ApplicationConfigCheck;
import com.qzc.cache.config.LocalCacheConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 基于Guava的本地缓存
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/12 14:15
 */
@Slf4j
@ApplicationConfigCheck(configClass = LocalCacheConfig.class, configServiceName = "本地缓存")
public class LocalCache {

    // CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
    private static Cache<String, String> loadingCache;

    static {
        if (LocalCacheConfig.isOpen_service()){
            log.debug("LocalCache builder");
            CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
                    // 设置要统计缓存的命中率
                    .recordStats()
                    // 设置缓存的移除通知
                    .removalListener(new RemovalListener<Object, Object>() {
                        @Override
                        public void onRemoval(RemovalNotification<Object, Object> notification) {
                            System.out.println(notification.getKey() + " 被移除, 移除原因： " + notification.getCause());
                        }
                    });

            // 设置缓存容器的初始容量, 默认为100
            int initialCapacity = LocalCacheConfig.getInitial_capacity() == null ? 100 : LocalCacheConfig.getInitial_capacity();
            builder.initialCapacity(initialCapacity);

            // 设置缓存容器的并发级别, 默认为8(并发级别是指可以同时写缓存的线程数)
            int concurrencyLevel = LocalCacheConfig.getConcurrency_level() == null ? 8 : LocalCacheConfig.getConcurrency_level();
            builder.concurrencyLevel(concurrencyLevel);

            // 设置缓存容器的过期更新时间，默认为300秒(如果缓存在300秒内没有更新，则失效)
            int DurationExpireAfterWrite = LocalCacheConfig.getDuration_expire_after_write() == null ? 300 : LocalCacheConfig.getDuration_expire_after_write();
            builder.expireAfterWrite(DurationExpireAfterWrite, TimeUnit.SECONDS);

            if (LocalCacheConfig.getMaximum_size() != null) {
                builder.maximumSize(LocalCacheConfig.getMaximum_size());
            }

            loadingCache = builder.build();
        }
    }

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
