package ca.jdelreyes.biddingbackend.dto.bid;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidResponse {
    private Integer id;
    private Double amount;
    private LocalDateTime bidAt;
    private AuctionResponse auction;
}
