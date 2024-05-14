package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.AbstractMySQLContainerTest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthRequest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthResponse;
import ca.jdelreyes.biddingbackend.dto.auth.RegisterRequest;
import ca.jdelreyes.biddingbackend.service.impl.JwtServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTests extends AbstractMySQLContainerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtServiceImpl jwtService;

    @Test
    void signUpShouldReturnTokenHavingSubjectAsEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpAsUser())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        AuthResponse authResponse = getAuthResponse(mvcResult);

        Assertions.assertThat(authResponse.getToken()).isNotNull();
        Assertions.assertThat(jwtService.extractUsername(authResponse.getToken())).isEqualTo("johndoe@email.com");
    }

    @Test
    void authenticateShouldReturnTokenHavingSubjectAsEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpAsUser())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginAsUser())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AuthResponse authResponse = getAuthResponse(mvcResult);

        Assertions.assertThat(authResponse.getToken()).isNotNull();
        Assertions.assertThat(jwtService.extractUsername(authResponse.getToken())).isEqualTo("johndoe@email.com");
    }

    @Test
    void authenticateAsAdminShouldReturnTokenHavingSubjectAsEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginAsAdmin())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        AuthResponse authResponse = getAuthResponse(mvcResult);

        Assertions.assertThat(authResponse.getToken()).isNotNull();
        Assertions.assertThat(jwtService.extractUsername(authResponse.getToken())).isEqualTo("admin@domain.ca");
    }

    private AuthResponse getAuthResponse(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(jsonString, AuthResponse.class);
    }

    private AuthRequest loginAsUser() {
        return AuthRequest.builder()
                .email("johndoe@email.com")
                .password("password")
                .build();
    }

    private RegisterRequest signUpAsUser() {
        return RegisterRequest.builder()
                .email("johndoe@email.com")
                .firstName("john")
                .lastName("doe")
                .password("password")
                .build();
    }

    private AuthRequest loginAsAdmin() {
        return AuthRequest.builder()
                .email("admin@domain.ca")
                .password("password")
                .build();
    }
}
