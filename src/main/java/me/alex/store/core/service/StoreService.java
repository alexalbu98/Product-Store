package me.alex.store.core.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.Store;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.StoreDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void createProductStore(String username, StoreDetails storeDetails) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Username does not exist."));

        if (storeRepository.existsByName(storeDetails.getName())) {
            throw new IllegalStateException("Store name already exists.");
        }

        storeRepository.save(new Store(null, null, user.getId(), storeDetails));
    }

    public List<StoreDto> findStores(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Username does not exist."));

        return storeRepository.findAllByUserRef(user.getId())
                .stream()
                .map(Store::getStoreDetails)
                .map(d -> new StoreDto(d.getName(), d.getDescription(), d.getAddress().toString()))
                .toList();
    }
}
