package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.user.ChangePasswordRequest;
import ca.jdelreyes.biddingbackend.dto.user.UpdateUserRequest;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") Integer id, @RequestBody UpdateUserRequest updateUserRequest) {
        return new ResponseEntity<>(userService.updateUser(id, updateUserRequest), HttpStatus.OK);
    }

    @PutMapping("change-password")
    public ResponseEntity<?> changeOwnPassword(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update-profile")
    public ResponseEntity<UserResponse> changeOwnProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody UpdateUserRequest updateUserRequest) {
        return new ResponseEntity<>(userService.updateOwnProfile(userDetails.getUsername(), updateUserRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
