package io.chillplus;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Map;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {

    static PostgreSQLContainer<?> db =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
                    .withDatabaseName("default")
                    .withUsername("quarkus")
                    .withPassword("quarkus");

    @Override
    public Map<String, String> start() {
        db.start();
        return Collections.singletonMap(
                "quarkus.datasource.url", db.getJdbcUrl()
        );
    }

    @Override
    public void stop() {
        db.stop();
    }
}
