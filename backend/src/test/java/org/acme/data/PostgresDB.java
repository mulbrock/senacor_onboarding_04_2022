package org.acme.data;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgresDB implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.2")
            .withDatabaseName("testDB")
            .withUsername("testuser")
            .withPassword("testpw");

    @Override
    public Map<String, String> start() {
        postgresContainer.start();

        Map<String, String> dbProperties = new HashMap<>();
        dbProperties.put("quarkus.datasource.username", postgresContainer.getUsername());
        dbProperties.put("quarkus.datasource.password", postgresContainer.getPassword());
        dbProperties.put("quarkus.datasource.jdbc.url", postgresContainer.getJdbcUrl());
        dbProperties.put("quarkus.hibernate-orm.log.sql", "false");
        return dbProperties;
    }

    @Override
    public void stop() {
        postgresContainer.stop();
    }
}
