package com.kjj.menu.service.cache;

import com.kjj.menu.service.MenuCacheService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheWarmUpServiceImpl implements CacheWarmUpService {

    private final MenuCacheService menuService;
    private final CacheManager cacheManager;

    @Override
    @PostConstruct
    public void warmUpCache() {
        String value = MenuCacheService.ALL_FOODS_CACHE_VALUE;
        String key = MenuCacheService.ALL_FOODS_CACHE_KEY;
        Cache cache = cacheManager.getCache(value);
        cache.put(key, menuService.getFood());
    }
}