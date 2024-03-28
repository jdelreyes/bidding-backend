package ca.jdelreyes.biddingbackend.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateItemRequest {
    private String name;
    private String description;
    private Double startBidAmount;
    private Double bidIncrement;
}
