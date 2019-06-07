package com.qzc.cache.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@Slf4j
/**
 *  localCache:
 *    ##是否启用服务
 *    open: true
 *    #设置缓存容器的初始容量, 默认为100
 *    initialCapacity: 300
 *    #设置缓存容器的并发级别, 默认为8(并发级别是指可以同时写缓存的线程数)
 *    concurrencyLevel: 8
 *    #设置缓存容器的过期更新时间，默认为300秒(如果缓存在300秒内没有更新，则失效)
 *    durationExpireAfterWrite: 100000
 */
public class LocalCacheConfig {

    @Value("${localCache.open:#{null}}")
    private String open;

    @Value("${localCache.initialCapacity:#{null}}")
    private Integer initialCapacity;

    @Value("${localCache.maximumSize:#{null}}")
    private Integer maximumSize;

    @Value("${localCache.concurrencyLevel:#{null}}")
    private Integer concurrencyLevel;

    @Value("${localCache.durationExpireAfterWrite:#{null}}")
    private Integer durationExpireAfterWrite;

    private static boolean open_service = false;

    private static Integer initial_capacity;

    private static Integer maximum_size;

    private static Integer concurrency_level;

    private static Integer duration_expire_after_write;

    @PostConstruct
    public void setStaticValue() {
        if (StringUtils.equals(this.getOpen(), "true")) {
            open_service = true;
        }

        if (open_service){
            initial_capacity = this.getInitialCapacity();
            maximum_size = this.getMaximumSize();
            concurrency_level = this.getConcurrencyLevel();
            duration_expire_after_write = this.getDurationExpireAfterWrite();
        }
    }

    public static boolean isOpen_service() {
        return open_service;
    }

    public static Integer getInitial_capacity() {
        return initial_capacity;
    }

    public static Integer getMaximum_size() {
        return maximum_size;
    }

    public static Integer getConcurrency_level() {
        return concurrency_level;
    }

    public static Integer getDuration_expire_after_write() {
        return duration_expire_after_write;
    }
}
