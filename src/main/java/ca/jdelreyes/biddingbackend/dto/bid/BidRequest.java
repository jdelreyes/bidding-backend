package ca.jdelreyes.biddingbackend.dto.bid;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidRequest {
    @NotNull
    private Integer itemId;
    @NotNull
    private Integer auctionId;
}
