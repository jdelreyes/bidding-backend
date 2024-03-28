package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
