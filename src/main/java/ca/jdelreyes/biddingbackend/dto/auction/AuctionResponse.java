package ca.jdelreyes.biddingbackend.dto.auction;

import ca.jdelreyes.biddingbackend.model.Bid;
import ca.jdelreyes.biddingbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponse {
    private Integer id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<Bid> bids;
    private User winner;
    private List<User> participants;
}
