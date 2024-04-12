package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.auth.AuthRequest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthResponse;
import ca.jdelreyes.biddingbackend.dto.auth.RegisterRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {
    private static String token;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    private int port;
    private final String url = "http://localhost:" + port;

    @Container
    public static MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"))
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

    @Test
    @Order(1)
    void signUp() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRegisterRequest())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(jsonString, AuthResponse.class);

        token = authResponse.getToken();

        System.out.println(authResponse.getToken());
    }

    @Test
    @Order(2)
    void getUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url + "/api/users")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();

        List<UserResponse> userResponseList = objectMapper.readValue(jsonString, new TypeReference<List<UserResponse>>() {
        });

        Assertions.assertThat(userResponseList).size().isEqualTo(2);
        Assertions.assertThat(userResponseList.get(1).getEmail()).isEqualTo(createRegisterRequest().getEmail());
    }

    @Test
    @Order(3)
    void loginAsAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url + "/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAdminAuthRequest())
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        AuthResponse authResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthResponse.class);
        token = authResponse.getToken();

        Assertions.assertThat(token).isNotNull();
    }

    @Test
    @Order(4)
    void updateUser() throws Exception {
    }

    private RegisterRequest createRegisterRequest() {
        return RegisterRequest.builder()
                .email("johndoe@email.com")
                .firstName("john")
                .lastName("doe")
                .password("password")
                .build();
    }

    private AuthRequest createAdminAuthRequest() {
        return AuthRequest.builder()
                .email("admin@domain.ca")
                .password("password")
                .build();
    }
}
