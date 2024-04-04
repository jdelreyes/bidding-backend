package ca.jdelreyes.biddingbackend.service.user;

import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUser(Integer id) throws UserNotFoundException;

    UserResponse changeOwnPassword(String userName, ChangePasswordRequest changePasswordRequest) throws Exception;

    UserResponse updateOwnProfile(String userName, UpdateUserRequest updateUserRequest) throws UserNotFoundException;

    UserResponse updateUser(Integer id, UpdateUserRequest updateUserRequest);

    void deleteUser(Integer id);
}
