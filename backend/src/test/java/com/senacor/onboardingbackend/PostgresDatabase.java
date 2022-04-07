package com.senacor.onboardingbackend;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgresDatabase implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.2")
        .withDatabaseName("test123db")
        .withUsername("test123user")
        .withPassword("test123pwd");

    @Override
    public Map<String, String> start() {
        postgres.start();

        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.username", postgres.getUsername());
        properties.put("quarkus.datasource.password", postgres.getPassword());
        properties.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        properties.put("quarkus.hibernate-orm.log.sql", "false"); // otherwise, too much loggin
        return properties;
    }

    @Override
    public void stop() {
        postgres.stop();
    }
}
