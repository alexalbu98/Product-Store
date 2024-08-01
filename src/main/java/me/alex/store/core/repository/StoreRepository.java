package me.alex.store.core.repository;

import me.alex.store.core.model.Store;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Stores s WHERE s.name = :name")
    boolean existsByName(String name);
}
