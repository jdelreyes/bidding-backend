package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.AbstractMySQLContainerTest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthRequest;
import ca.jdelreyes.biddingbackend.dto.auth.AuthResponse;
import ca.jdelreyes.biddingbackend.dto.auth.RegisterRequest;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
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
public class ItemControllerTests extends AbstractMySQLContainerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getItemsShouldReturnItemResponseList() throws Exception {
        String token = authenticateAsAdmin();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/items")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<ItemResponse> itemResponseList =
                objectMapper
                        .readValue(mvcResult
                                .getResponse()
                                .getContentAsString(), new TypeReference<List<ItemResponse>>() {
                        });

        Assertions.assertThat(itemResponseList.size()).isEqualTo(0);
    }

//    @Test
//    void getItemShouldReturnItemResponse

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
}
