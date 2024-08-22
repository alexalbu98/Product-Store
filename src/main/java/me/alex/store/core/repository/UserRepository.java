package me.alex.store.core.repository;

import java.util.Optional;
import me.alex.store.core.model.AppUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END"
      + " FROM Users u WHERE u.username = :username")
  boolean existsByUsername(String username);
}
