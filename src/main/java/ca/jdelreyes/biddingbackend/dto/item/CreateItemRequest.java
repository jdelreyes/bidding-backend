package ca.jdelreyes.biddingbackend.dto.item;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private Double startBidAmount;
    @NotEmpty
    private Double bidIncrement;
//    @ManyToOne(cascade = CascadeType.ALL)
//    private User user;
//
//    @ManyToOne
//    private Category category;
}
