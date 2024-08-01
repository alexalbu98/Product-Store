package me.alex.store.core.repository;

import me.alex.store.core.model.Store;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends ListCrudRepository<Store, Long> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stores s WHERE s.name = :name")
    boolean existsByName(String name);

    List<Store> findAllByUserRef(Long userRef);
}
