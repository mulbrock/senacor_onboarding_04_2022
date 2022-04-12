package net.bmw;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.;import java.util.Collections;
import java.util.Map;


public class TestContainersDInitializer implements QuarkusTestResourceLifecycleManager {
    static PostgreSQLContainer<?> postgresDb = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("quarkus_db")
            .withUsername("postgres")
            .withPassword("postgres");

    @Override
    public Map<String, String> start() {
        postgresDb.start();
        return Collections.singletonMap("quarkus.datasource.url", postgresDb.getJdbcUrl());
    }

    @Override
    public void stop() {
        postgresDb.stop();
    }
}
