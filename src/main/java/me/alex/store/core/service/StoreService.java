package me.alex.store.core.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

}
