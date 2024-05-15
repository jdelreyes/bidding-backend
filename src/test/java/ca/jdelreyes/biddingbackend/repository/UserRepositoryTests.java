package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.model.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"repository-test"})
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void SaveUser_Returns_User() {
        User user = createUser();
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void FindUserById_Equals_UserCreated() throws UserNotFoundException {
        User savedUser = userRepository.save(createUser());

        User user = userRepository
                .findUserById(savedUser.getId())
                .orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void FindUserByEmail_Equals_UserCreated() throws UserNotFoundException {
        User savedUser = userRepository.save(createUser());

        User user = userRepository
                .findUserByEmail(savedUser.getEmail())
                .orElseThrow(UserNotFoundException::new);

        Assertions.assertThat(user.getEmail()).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void DeleteUser_Returns_OptionalEmpty() throws UserNotFoundException {
        User savedUser = userRepository.save(createUser());

        userRepository.deleteUserById(savedUser.getId());

        Assertions.assertThat(userRepository.findUserById(savedUser.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void ExistsById_Equals_True() {
        User savedUser = userRepository.save(createUser());

        boolean userExists = userRepository.existsById(savedUser.getId());

        Assertions.assertThat(userExists).isTrue();
    }

    private User createUser() {
        return User
                .builder()
                .email("user@domain.ca")
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .role(Role.USER)
                .build();
    }

}
