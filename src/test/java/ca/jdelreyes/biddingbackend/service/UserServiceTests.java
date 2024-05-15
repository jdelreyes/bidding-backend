package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.model.enums.Role;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import ca.jdelreyes.biddingbackend.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void GetUsers_Returns_UserResponseList() {
        List<UserResponse> userResponseList = userService.getUsers();

        Assertions.assertThat(userResponseList.size()).isEqualTo(0);
    }

    private User createUser() {
        return User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("email@domain.ca")
                .role(Role.USER)
                .password("password")
                .build();
    }
}
