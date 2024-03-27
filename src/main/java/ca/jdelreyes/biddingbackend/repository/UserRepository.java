package ca.jdelreyes.biddingbackend.repository;

import ca.jdelreyes.biddingbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

}