package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(Integer id);

    Optional<User> findUserByEmail(String userName);

    void deleteUserById(@NonNull Integer id);

    boolean existsById(@NonNull Integer id);
}
