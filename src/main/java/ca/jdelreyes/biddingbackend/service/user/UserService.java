package ca.jdelreyes.biddingbackend.service.user;

import ca.jdelreyes.biddingbackend.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers();

    UserResponse updateUser();

    void deleteUser();
}
