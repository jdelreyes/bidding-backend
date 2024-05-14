package ca.jdelreyes.biddingbackend.service.impl;

import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.exception.PasswordNotMatch;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import ca.jdelreyes.biddingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    @Override
    public UserResponse getUser(Integer id) throws UserNotFoundException {
        return mapUserToUserResponse(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponse getUserByEmail(String email) throws UserNotFoundException {
        return mapUserToUserResponse(userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponse changeOwnPassword(Integer id, ChangePasswordRequest changePasswordRequest) throws Exception {
        User user = userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            throw new PasswordNotMatch();

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return mapUserToUserResponse(user);
    }

    @Override
    public UserResponse updateOwnProfile(Integer id, UpdateUserRequest updateUserRequest)
            throws UserNotFoundException {
        User user = userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setEmail(updateUserRequest.getEmail());

        userRepository.save(user);

        return mapUserToUserResponse(user);
    }


    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow();

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setEmail(updateUserRequest.getEmail());

        return mapUserToUserResponse(user);
    }


    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteUserById(id);
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .joinedAt(user.getJoinedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole())
                .build();
    }
}
