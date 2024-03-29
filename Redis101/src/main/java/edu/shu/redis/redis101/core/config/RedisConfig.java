package edu.shu.redis.redis101.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@PropertySource(ignoreResourceNotFound = true, value = "classpath:cache-redis.properties")
public class RedisConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${runtime.cache.ttl: 24}")
    private int cacheTTL;

    @Value("${runtime.cache.redis.hostname: localhost}")
    private String redisHostName;

    @Value("${runtime.cache.redis.port: 6379}")
    private int redisPort;

    @Value("${runtime.cache.redis.password}")
    private String redisPassword;

    @Value("${runtime.cache.redis.prefix}")
    private String redisPrefix;

    @Value("#{${runtime.cache.redis.cacheTtls}}")
    private Map<String, Long> cacheTtls = new HashMap<>();

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        LOG.info(String.format("Host address is %s",redisHostName));
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName,
                redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    @Profile({"dev", "test"})
    public CacheManager defaultCacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean(name = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    @Profile("dev-redis")
    public CacheManager cacheManagerDevLocal(RedisConnectionFactory redisConnectionFactory) {
        final Duration expiration = Duration.ofSeconds(cacheTTL);
        return createCacheManager(redisConnectionFactory, expiration);
    }

    /* Creates cache manager based on params provided */
    private CacheManager createCacheManager(final RedisConnectionFactory redisConnectionFactory, final Duration expiration) {
        final Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        for (Map.Entry<String, Long> cacheNameAndTimeout : cacheTtls.entrySet()) {
            cacheConfigs.put(cacheNameAndTimeout.getKey(),
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(cacheNameAndTimeout.getValue())));
        }
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .prefixKeysWith(redisPrefix)
                                .entryTtl(expiration))
                .withInitialCacheConfigurations(cacheConfigs)
                .transactionAware()
                .build();
    }
}
