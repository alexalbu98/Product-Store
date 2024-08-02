package me.alex.store.core.repository;

import me.alex.store.core.model.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Products p " +
            "WHERE p.name = :name and p.store_ref = :storeId")
    boolean existsByNameInStore(String name, Long storeId);

    @Query("SELECT * FROM Products  WHERE store_ref = :storeRef")
    List<Product> findAllByStoreRef(Long storeRef);

    @Query("SELECT * FROM Products " +
            "WHERE store_ref = :storeRef and name LIKE CONCAT('%',:name,'%')")
    List<Product> findAllByStoreRefAndName(Long storeRef, String name);
}
