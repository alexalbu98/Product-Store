package me.alex.store;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

public abstract class AbstractPostgresTest {
    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("store-db")
            .withUsername("sa")
            .withPassword("sa")
            .withReuse(true);

    @BeforeAll
    public static void startPostgresContainer() {
        container.setPortBindings(List.of("5432:5432"));
        container.start();
    }

}
