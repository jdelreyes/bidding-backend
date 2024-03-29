package ca.jdelreyes.biddingbackend.dto.user;

import ca.jdelreyes.biddingbackend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime dateTimeCreated;
    private Role role;
}
