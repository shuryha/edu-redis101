package edu.shu.redis102.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(CacheConfigProperties.class)
@Slf4j
public class CacheConfig {

    private static RedisCacheConfiguration createCacheConfig(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(final CacheConfigProperties props) {
        log.info("Redis (/Lettuce) configuration enabled. With cache timeout: '{}'", props.getTimeoutSeconds());
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(props.getRedisHost());
        redisStandaloneConfiguration.setPort(props.getRedisPort());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(final RedisConnectionFactory cf) {
        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(final CacheConfigProperties props) {
        return createCacheConfig(props.getTimeoutSeconds());
    }

    @Bean
    public CacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory, final CacheConfigProperties props) {
        final Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        for (Map.Entry<String, Long> cacheNameAndTimeout : props.getCacheExpirations().entrySet()) {
            cacheConfigs.put(cacheNameAndTimeout.getKey(), createCacheConfig(cacheNameAndTimeout.getValue()));
        }
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration(props))
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
