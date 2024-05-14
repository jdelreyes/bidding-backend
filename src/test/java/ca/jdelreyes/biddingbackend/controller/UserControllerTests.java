package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.AbstractMySQLContainerTest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthRequest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthResponse;
import ca.jdelreyes.biddingbackend.dto.auth.RegisterRequest;
import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.service.impl.JwtServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests extends AbstractMySQLContainerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtServiceImpl jwtService;

    @Test
    void registerAsUserShouldReturnToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        AuthResponse authResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AuthResponse.class);

        Assertions.assertThat(authResponse.getToken()).isNotNull();
        Assertions.assertThat(jwtService
                .extractUsername(authResponse.getToken())).isEqualTo(registerRequest().getEmail());
    }

    @Test
    void getUsersShouldReturnAListOfUsers() throws Exception {
        String token = registerAsUser();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<UserResponse> userResponseList = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<UserResponse>>() {
                });

        Assertions.assertThat(userResponseList.size()).isEqualTo(2);
    }

    @Test
    void getUserByEmailShouldReturnAUserResponseObject() throws Exception {
        String token = registerAsUser();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users?userName=" + jwtService.extractUsername(token))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponse userResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getEmail()).isEqualTo(jwtService.extractUsername(token));
    }

    @Test
    void changePasswordShouldReturnAUserResponseObject() throws Exception {
        String token = registerAsUser();

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/change-password")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest())))
                // assertions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void updateProfileShouldReturnAUserResponseObject() throws Exception {
        String token = registerAsUser();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/update-profile")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponse userResponse =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

        Assertions.assertThat(userResponse).isNotNull();
//        assert that all fields are updated
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo(updateUserRequest().getFirstName());
        Assertions.assertThat(userResponse.getLastName()).isEqualTo(updateUserRequest().getLastName());
        Assertions.assertThat(userResponse.getEmail()).isEqualTo(updateUserRequest().getEmail());
    }

    @Test
    void deleteUserAsAdminShouldReturnNothing() throws Exception {
        String userToken = registerAsUser();
        String adminToken = authenticateAsAdmin();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users?userName=" + jwtService.extractUsername(userToken))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponse userResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/" + userResponse.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }

    @Test
    void updateUserAsAdminShouldReturnUserResponse() throws Exception {
        String userToken = registerAsUser();
        String adminToken = authenticateAsAdmin();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users?userName=" + jwtService.extractUsername(userToken))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Integer userId =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class).getId();

        mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/users/" + userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserResponse userResponse
                = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

        Assertions.assertThat(userResponse).isNotNull();
//        assert that all fields are updated
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo(updateUserRequest().getFirstName());
        Assertions.assertThat(userResponse.getLastName()).isEqualTo(updateUserRequest().getLastName());
        Assertions.assertThat(userResponse.getEmail()).isEqualTo(updateUserRequest().getEmail());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    protected String registerAsUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        AuthResponse authResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AuthResponse.class);

        return authResponse.getToken();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    protected String authenticateAsAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestAsAdmin())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AuthResponse authResponse = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), AuthResponse.class);

        return authResponse.getToken();
    }

    private RegisterRequest registerRequest() {
        return RegisterRequest.builder()
                .email("johndoe@email.com")
                .firstName("john")
                .lastName("doe")
                .password("password")
                .build();
    }

    private AuthRequest authRequestAsAdmin() {
        return AuthRequest.builder()
                .email("admin@domain.ca")
                .password("password")
                .build();
    }

    private ChangePasswordRequest changePasswordRequest() {
        return ChangePasswordRequest.builder()
                .oldPassword("password")
                .newPassword("newPassword")
                .build();
    }

    private UpdateUserRequest updateUserRequest() {
        return UpdateUserRequest.builder()
                .email("updateJonhdoe@email.com")
                .firstName("updatedJohn")
                .lastName("updatedDoe")
                .build();
    }
}
