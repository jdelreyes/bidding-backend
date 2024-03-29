package ca.jdelreyes.biddingbackend.service.user;

import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    @Override
    public UserResponse updateUser() {
        return null;
    }

    @Override
    public void deleteUser() {
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dateTimeCreated(user.getDateTimeCreated())
                .role(user.getRole())
                .build();
    }
}
