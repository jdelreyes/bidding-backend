package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse getUser(Integer id) throws UserNotFoundException;

    UserResponse changeOwnPassword(Integer id, ChangePasswordRequest changePasswordRequest) throws Exception;

    UserResponse updateOwnProfile(Integer id, UpdateUserRequest updateUserRequest) throws UserNotFoundException;

    UserResponse updateUser(Integer id, UpdateUserRequest updateUserRequest);

    void deleteUser(Integer id);
}
