package edu.shu.redis.redis101.service;

import edu.shu.redis.redis101.core.config.CacheConsts;
import edu.shu.redis.redis101.model.BasketContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

import static edu.shu.redis.redis101.core.config.CacheConsts.DUMMY_BASKET_CONTENT_MIN_CACHE;

@Service
public class BasketContentDummyGen {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final int BASKET_ITEMS_MIN = 500;

    public static final int BASKET_ITEMS_AVG = 10000;

    public static final int BASKET_ITEMS_MAX = 15000;

    public static final int BASKET_EXPIRATION_MILLIS = 300000;

    public static final int SOD_ID_BOUND = 2000;

    @Cacheable(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MIN_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent>
    genDummyTestDataMin() {
        return genDummyTestData(BASKET_ITEMS_MIN);
    }

    @Cacheable(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MIN_CACHE2, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY2, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> genDummyTestDataMin2() {
        return genDummyTestData(BASKET_ITEMS_MIN);
    }

    @Cacheable(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MAX_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> genDummyTestDataMax() {
        return genDummyTestData(BASKET_ITEMS_MAX);
    }

    @CachePut(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MAX_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> deleteBasket(final int basketId, final List<BasketContent> basketContents) {
        return basketContents.stream().filter(bc -> bc.getBasketId() != basketId).collect(Collectors.toList());
    }

    @CachePut(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MAX_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> deleteBasketItem(final int basketItemId, final List<BasketContent> basketContents) {
        return basketContents.stream().filter(bc -> bc.getBasketItemId() != basketItemId).collect(Collectors.toList());
    }

    @CachePut(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MAX_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> updateBasketItem(final int basketItemId, final Integer timeoutExtensionSeconds, final List<BasketContent> basketContents) {
        return basketContents.stream().filter(bc -> bc.getBasketItemId() == basketItemId)
                .map(bc-> { bc.setExpiryTimestamp(new Date(bc.getExpiryTimestamp().getTime() + timeoutExtensionSeconds.intValue() * 1000)); return bc;})
                .collect(Collectors.toList());
    }

    @CachePut(cacheNames = CacheConsts.DUMMY_BASKET_CONTENT_MAX_CACHE, key = CacheConsts.DUMMY_BASKET_CONTENT_KEY, cacheManager = CacheConsts.CACHE_MANAGER_BEAN_NAME)
    public List<BasketContent> saveBasketItem(final BasketContent bc, final List<BasketContent> basketContents) {
        basketContents.add(bc);
        return basketContents;
    }

    @CacheEvict(value = DUMMY_BASKET_CONTENT_MIN_CACHE)
    public void clearCache() {
    }

    public List<BasketContent> findAllBasketContents(final List<Long> sodInstanceIds, final Date date, final List<BasketContent> basketContents) {
        Set<Long> sodInstanceSet = new HashSet<>(sodInstanceIds);
        return basketContents.stream().filter(bc -> sodInstanceSet.contains(bc.getSodInstanceId()) && bc.getExpiryTimestamp().after(date)).collect(Collectors.toList());
    }

    private List<BasketContent> genDummyTestData(final int numOfTestBasketItems) {
        log.debug("Generating {} dummy basket contents", numOfTestBasketItems);
        final int basketIdBound = numOfTestBasketItems / 3;
        final Random basketIdGen = new Random();
        final Random sodIdGen = new Random();
        final Random countGen = new Random();

        final List<BasketContent> resBasketContents = new ArrayList<>(numOfTestBasketItems);
        for (int i = 0; i < numOfTestBasketItems; ++i) {
            BasketContent bc = new BasketContent();
            bc.setBasketId(basketIdGen.nextInt(basketIdBound));
            bc.setExpiryTimestamp((i % 5 == 0) ? new Date() : new Date(System.currentTimeMillis() + BASKET_EXPIRATION_MILLIS));
            bc.setBasketItemId(i);
            bc.setSodInstanceId(sodIdGen.nextInt(SOD_ID_BOUND));
            bc.setPaxCount(countGen.nextInt(100));
            bc.setConcessionCount(countGen.nextInt(10));
            bc.setNusCount(countGen.nextInt(15));
            bc.setDisableCount(countGen.nextInt(5));
            bc.setOtherDisabledPassengersCount(countGen.nextInt(5));
            bc.setPcaCount(countGen.nextInt(100));
            bc.setReservableCount(countGen.nextInt(10));
            bc.setItemTypeCode(((i % 100) != 0) ? "SOD" : "CS");
            resBasketContents.add(bc);
        }
        return resBasketContents;
    }
}
