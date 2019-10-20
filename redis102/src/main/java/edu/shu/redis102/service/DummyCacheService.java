package edu.shu.redis102.service;

import edu.shu.redis102.config.CacheConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DummyCacheService {
    @Cacheable(cacheNames = CacheConsts.CACHE_THIS_NAME)
    public String cacheThis() {
        log.info("Cache this miss. Getting fresh data.");
        return "this is it";
    }

    @Cacheable(cacheNames = CacheConsts.CACHE_THAT_NAME)
    public String cacheThat() {
        log.info("Cache that miss. Getting fresh data.");
        return "this is that";
    }

    @CacheEvict(cacheNames = CacheConsts.CACHE_THIS_NAME)
    public void cleanCacheThis() {
        log.info("Cache this cleared.");
    }

    @CacheEvict(cacheNames = CacheConsts.CACHE_THAT_NAME)
    public void cleanCacheThat() {
        log.info("Cache that cleared.");
    }
}
