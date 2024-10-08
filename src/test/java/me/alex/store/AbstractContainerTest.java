package me.alex.store;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainerTest {

  protected final static GenericContainer<?> redis =
      new GenericContainer<>(DockerImageName.parse("redis:7.2.4-alpine"))
          .withExposedPorts(6379);
  private final static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
      "postgres:latest")
      .withDatabaseName("store-db")
      .withUsername("sa")
      .withPassword("sa")
      .withReuse(true);

  @BeforeAll
  static protected void startRedisContainer() {
  }

  @BeforeAll
  public static void startPostgresContainer() {
    postgresContainer.setPortBindings(List.of("5432:5432"));
    postgresContainer.start();
    redis.setPortBindings(List.of("6379:6379"));
    redis.start();
  }

}
