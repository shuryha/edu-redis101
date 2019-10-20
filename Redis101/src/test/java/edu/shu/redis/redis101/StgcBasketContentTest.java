package edu.shu.redis.redis101;

import edu.shu.redis.redis101.core.config.CacheConsts;
import edu.shu.redis.redis101.model.BasketContent;
import edu.shu.redis.redis101.service.BasketContentDummyGen;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;


@ActiveProfiles("dev-redis-local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StgcBasketContentTest {
    @Autowired
    private BasketContentDummyGen basketContentDummyGen;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test_basketContentMin() {
        final List<BasketContent> basketContents = basketContentDummyGen.genDummyTestDataMin();
        Assert.assertFalse(CollectionUtils.isEmpty(basketContents));
        final int basketContentSize = basketContents.size();

        final List<BasketContent> basketContentsUnmod = basketContentDummyGen.genDummyTestDataMin();
        final int basketContentSizeUnmod = basketContents.size();
        Assert.assertEquals(basketContentSize, basketContentSizeUnmod);

        final BasketContent testBasketContent = basketContentsUnmod.get(basketContents.size() - 1);
        final int basketId = testBasketContent.getBasketId();
        basketContentDummyGen.deleteBasket(basketId, basketContentsUnmod);

        final List<BasketContent> basketContentMod1 = basketContentDummyGen.genDummyTestDataMin();
        final int basketContentMod1Size = basketContentMod1.size();
        Assert.assertTrue(basketContentSizeUnmod > basketContentMod1Size);
    }

    @Test
    public void test_basketContentMax() {
        List<BasketContent> basketContent = basketContentDummyGen.genDummyTestDataMax();
        Assert.assertFalse(CollectionUtils.isEmpty(basketContent));
    }

    @Test
    public void test_basketContentMin2() {
        final List<BasketContent> basketContents = basketContentDummyGen.genDummyTestDataMin();
        final List<BasketContent> basketContents2 = basketContentDummyGen.genDummyTestDataMin2();
        System.out.println("Known caches:");
        cacheManager.getCacheNames().stream().forEach(System.out::println);
        //cacheManager.getCache(CacheConsts.DUMMY_BASKET_CONTENT_MIN_CACHE).clear();
        basketContentDummyGen.clearCache();
    }
}