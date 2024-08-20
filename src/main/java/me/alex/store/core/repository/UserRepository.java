package me.alex.store.core.repository;

import java.util.Optional;
import me.alex.store.core.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END"
      + " FROM Users u WHERE u.username = :username")
  boolean existsByUsername(String username);
}
