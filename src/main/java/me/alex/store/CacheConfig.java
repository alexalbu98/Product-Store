package me.alex.store;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
@NoArgsConstructor
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
  @Primary
  public CacheManager caffeineCacheManager() {
    SimpleCacheManager manager = new SimpleCacheManager();
    manager.setCaches(List.of(productCache(), storeCache()));
    return manager;
  }

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .disableCachingNullValues()
        .serializeValuesWith(RedisSerializationContext
            .SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory,
      RedisCacheConfiguration redisCacheConfiguration) {
    return RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(connectionFactory)
        .withCacheConfiguration("storeCache", redisCacheConfiguration)
        .withCacheConfiguration("productCache", redisCacheConfiguration)
        .build();
  }
}
