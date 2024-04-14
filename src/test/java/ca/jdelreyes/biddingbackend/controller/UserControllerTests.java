package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.AbstractMySQLContainerTest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthRequest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthResponse;
import ca.jdelreyes.biddingbackend.dto.auth.RegisterRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests extends AbstractMySQLContainerTest {
    private static String token;
    private static Integer userId;
    //    controller-specific
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @LocalServerPort
    private int port;
    private final String url = "http://localhost:" + port;


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

        userId = userResponseList.get(1).getId();

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

        Assertions.assertThat(authResponse.getToken()).isNotNull();
    }

    @Test
    @Order(4)
    void updateUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url + "/api/users/" + userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUpdateUserRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();

        UserResponse userResponse = objectMapper.readValue(jsonString, UserResponse.class);

        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getEmail()).isEqualTo(createUpdateUserRequest().getEmail());
    }

    @Test
    @Order(5)
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/api/users/" + userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
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

    private UpdateUserRequest createUpdateUserRequest() {
        return UpdateUserRequest
                .builder()
                .email("jamesmark@email.ca")
                .firstName("james")
                .lastName("mark")
                .build();
    }
}
