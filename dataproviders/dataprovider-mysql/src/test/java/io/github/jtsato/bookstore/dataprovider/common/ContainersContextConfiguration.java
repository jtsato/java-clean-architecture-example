package io.github.jtsato.bookstore.dataprovider.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

/**
 * @author Jorge Takeshi Sato
 */

public abstract class ContainersContextConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ContainersContextConfiguration.class);

    private static MySQLContainer<?> mySQLContainer;

    public static MySQLContainer<?> getInstance() {
        if (mySQLContainer == null) {
            mySQLContainer = new MySQLContainer<>();
            mySQLContainer.withStartupTimeoutSeconds(900);
            mySQLContainer.withUrlParam("serverTimezone", "America/Sao_Paulo");
            mySQLContainer.start();
            log.info("Starting Container -> getInstance() with url={}, user={}, password={}",
                     mySQLContainer.getJdbcUrl(),
                     mySQLContainer.getUsername(),
                     mySQLContainer.getPassword());
        }
        return mySQLContainer;
    }

    @DynamicPropertySource
    static void databaseProperties(final DynamicPropertyRegistry registry) {
        final MySQLContainer<?> intance = getInstance();;
        registry.add("spring.datasource.url", intance::getJdbcUrl);
        registry.add("spring.datasource.username", intance::getUsername);
        registry.add("spring.datasource.password", intance::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
}
