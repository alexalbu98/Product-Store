package me.alex.store;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CaffeineCache storeCache() {
        return new CaffeineCache("storeCache", Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(60))
                .initialCapacity(1)
                .maximumSize(6000)
                .build());
    }

    @Bean
    public CaffeineCache productCache() {
        return new CaffeineCache("productCache", Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(60))
                .initialCapacity(1)
                .maximumSize(6000)
                .build());
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(productCache(), storeCache()));
        return manager;
    }
}
