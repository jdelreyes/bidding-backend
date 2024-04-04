package ca.jdelreyes.biddingbackend.dto.bid;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.model.Auction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidResponse {
    private Integer id;
    private Double amount;
    private AuctionResponse auction;
}
