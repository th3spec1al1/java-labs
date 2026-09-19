package ru.kurbanov;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kurbanov.config.TestSecurityConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@EnableJpaRepositories("ru.kurbanov.application.abstractions.repositories.jpa")
@EntityScan("ru.kurbanov.infrastructure.persistence.jpa.model")
@Testcontainers
public class TestCarDealershipApplication {

    static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.change-log",
                () -> "classpath:db/changelog/db.changelog-master.yml");
        registry.add("spring.kafka.bootstrap-servers", () -> "localhost:29092");
    }

    @Test
    void canConnectAndQuery() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword())) {

            ResultSet rs = connection.createStatement().executeQuery("select 1");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }
}