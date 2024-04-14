package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.model.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveUser() {
        User user = createUser();

        user = entityManager.persist(user);

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void contextLoads() {

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
