package ca.jdelreyes.biddingbackend.event;

import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidCreatedEvent {
    private BidResponse bidResponse;
}
