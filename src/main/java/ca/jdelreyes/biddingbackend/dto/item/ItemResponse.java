package ca.jdelreyes.biddingbackend.dto.item;

import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.model.Category;
import ca.jdelreyes.biddingbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private Integer id;
    private String name;
    private String description;
    private Double startBidAmount;
    private Double finalBidAmount;
    private Double bidIncrement;
    private LocalDateTime createAt;
    private UserResponse seller;
    private Category category;
}
