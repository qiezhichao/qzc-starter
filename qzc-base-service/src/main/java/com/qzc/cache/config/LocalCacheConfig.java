package com.qzc.cache.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class LocalCacheConfig {

    @Value("${localCache.initialCapacity:#{null}}")
    private Integer initialCapacity;

    @Value("${localCache.maximumSize:#{null}}")
    private Integer maximumSize;

    @Value("${localCache.concurrencyLevel:#{null}}")
    private Integer concurrencyLevel;

    @Value("${localCache.durationExpireAfterWrite:#{null}}")
    private Integer durationExpireAfterWrite;

    private static Integer initial_capacity;

    private static Integer maximum_size;

    private static Integer concurrency_level;

    private static Integer duration_expire_after_write;

    @PostConstruct
    public void setStaticValue() {
        initial_capacity = this.getInitialCapacity();
        maximum_size = this.getMaximumSize();
        concurrency_level = this.getConcurrencyLevel();
        duration_expire_after_write = this.getDurationExpireAfterWrite();
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
