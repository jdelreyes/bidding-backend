package ca.jdelreyes.biddingbackend;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractMySQLContainerTest {
    @Container
    public static org.testcontainers.containers.MySQLContainer<?> mySQLContainer =
            new org.testcontainers.containers.MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"))
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test")
                    .waitingFor(Wait.forListeningPort())
                    .withEnv("MYSQL_ROOT_HOST", "%");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
}
