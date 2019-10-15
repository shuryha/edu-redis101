package edu.shu.redis.redis101.service;

import edu.shu.redis.redis101.core.config.CacheConsts;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedisCacheDummy101Service {
    @Cacheable(value = CacheConsts.DUMMY_CACHE_101, key="{#key}", cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public String createNewKeyValue(String key) {
        return "Value@" + new Date().getTime();
    }
}
