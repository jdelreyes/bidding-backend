package ca.jdelreyes.biddingbackend.dto.item;

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
    private Double bidIncrement;
    private LocalDateTime dateTimeCreated;

}
