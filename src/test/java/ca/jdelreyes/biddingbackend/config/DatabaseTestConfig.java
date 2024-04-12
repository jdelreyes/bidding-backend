package ca.jdelreyes.biddingbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class DatabaseTestConfig {
    @Bean
    public MySQLContainer<?> mySQLContainer() {
        MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        container.start();
        return container;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
