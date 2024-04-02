package ca.jdelreyes.biddingbackend.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionRequest {
    private Integer itemId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
