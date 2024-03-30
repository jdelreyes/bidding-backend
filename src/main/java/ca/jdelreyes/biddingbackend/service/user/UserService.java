package ca.jdelreyes.biddingbackend.service.user;

import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUser(Integer id);

    UserResponse changeOwnPassword(String userName, ChangePasswordRequest changePasswordRequest);

    UserResponse updateOwnProfile(String userName, UpdateUserRequest updateUserRequest);

    UserResponse updateUser(Integer id, UpdateUserRequest updateUserRequest);

    void deleteUser(Integer id);
}
