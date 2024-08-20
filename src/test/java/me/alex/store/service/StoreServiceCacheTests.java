package me.alex.store.service;

import static me.alex.store.TestData.testStore;
import static me.alex.store.TestData.testStoreOwnerUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import me.alex.store.AbstractContainerTest;
import me.alex.store.core.model.User;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.core.service.StoreService;
import me.alex.store.rest.dto.StoreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class StoreServiceCacheTests extends AbstractContainerTest {

  @Autowired
  StoreService service;

  @Autowired
  UserRepository userRepository;

  @SpyBean
  StoreRepository storeRepository;

  @Autowired
  @Qualifier("caffeineCacheManager")
  CacheManager caffeineCacheManager;

  @Autowired
  @Qualifier("redisCacheManager")
  CacheManager redisCacheManager;

  private User user;

  @BeforeEach
  void createUser() {
    user = testStoreOwnerUser();
    user = userRepository.save(user);
  }

  @Test
  @DisplayName("When a query is execute both cache layers store the data.")
  void both_caches_work() {
    service.openProductStore(user.getUsername(), testStore(user.getId()).getStoreDetails());

    var memCacheMiss = service.findUserStores(user.getUsername());
    var memCacheHit = service.findUserStores(user.getUsername());

    verify(storeRepository, times(1)).findAllByUserRef(anyLong());
    assertThat(memCacheHit).isEqualTo(memCacheMiss);
    assertThat(caffeineCacheManager.getCache("storeCache").get(user.getUsername()).get()).isEqualTo(
        memCacheHit);
    var redisCacheHit = (StoreDto[]) redisCacheManager.getCache("storeCache")
        .get(user.getUsername()).get();
    assertArrayEquals(redisCacheHit, memCacheHit);
  }
}
