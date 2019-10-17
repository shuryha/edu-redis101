package edu.shu.redis.redis101.service;

import edu.shu.redis.redis101.core.config.CacheConsts;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

@Service
public class RedisCacheDummy101Service {
    @Cacheable(value = CacheConsts.DUMMY_CACHE_101, key="{#key}", cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public String createNewKeyValue(String key) {
        return "Value@" + new Date().getTime();
    }

    @Cacheable(value = CacheConsts.DUMMY_CACHE_102, key="{#key}", cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public Object createNewKeyValueObject(String key) {
        return new TestObject101("Value1@" + new Date().getTime(), "Value2@" + new Date().getTime());
    }
}
