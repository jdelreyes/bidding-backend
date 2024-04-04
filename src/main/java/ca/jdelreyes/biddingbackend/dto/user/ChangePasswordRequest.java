package ca.jdelreyes.biddingbackend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotEmpty
    @Size(min = 8, max = 256)
    private String oldPassword;
    @NotEmpty
    @Size(min = 8, max = 256)
    private String newPassword;
}
