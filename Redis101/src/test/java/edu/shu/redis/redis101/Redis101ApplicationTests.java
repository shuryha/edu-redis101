package edu.shu.redis.redis101;

import edu.shu.redis.redis101.core.config.CacheConsts;
import edu.shu.redis.redis101.service.RedisCacheDummy101Service;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Redis101ApplicationTests {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${spring.cache.type}")
	private String springCacheType;

	@Autowired
	private RedisCacheDummy101Service cacheDummyService;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void test_redisTemplate() {
		final ValueOperations ops = redisTemplate.opsForValue();
		ops.set("key1", "value1");
		ops.set("key2", "value2");
		ops.set("key3", "value3");
		ops.set("key4", "value4");
		final String val = (String)ops.get("key1");
		Assert.assertEquals("value1", val);
	}

	@Test
	public void test_dummyCacheService() {
        System.out.println("Known caches: ");
        cacheManager.getCacheNames().stream().forEach(System.out::println);

        initCache();

        final String val2 = cacheDummyService.createNewKeyValue("key1");
		System.out.println("Date now: '" + new Date().getTime() + "'");
		System.out.println("Value 1-1: '" + val2 + "'");

		System.out.println("Known caches2: ");
		cacheManager.getCacheNames().stream().forEach(System.out::println);

		final Cache cache = cacheManager.getCache("");
        // final RedisTemplate<String, Object> template = (RedisTemplate<String, Object>)cache.getNativeCache();
        final Set<String> keys = redisTemplate.keys(CacheConsts.DUMMY_CACHE_101);
        Assert.assertFalse(CollectionUtils.isEmpty(keys));
        while (keys.iterator().hasNext()) {
            final String key = keys.iterator().next();
            final String val = (String)redisTemplate.opsForValue().get(key);
            System.out.println("Value: " + val);
        }
	}

    @Test
    public void test_dummyCacheService2() {
	    initCache2();
    }

    private void initCache() {
        final String val1 = cacheDummyService.createNewKeyValue("key1");
        System.out.println("Value 1-0: '" + val1 + "'");
        cacheDummyService.createNewKeyValue("key2");
        cacheDummyService.createNewKeyValue("key3");
        cacheDummyService.createNewKeyValue("key4");
    }

    private void initCache2() {
        final String val1 = cacheDummyService.createNewKeyValue("key1");
        System.out.println("Value 1-0: '" + val1 + "'");
        cacheDummyService.createNewKeyValueObject("key2");
        cacheDummyService.createNewKeyValueObject("key3");
        cacheDummyService.createNewKeyValueObject("key4");
    }
}
