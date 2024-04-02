package ca.jdelreyes.biddingbackend.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private Double startBidAmount;
    private Double currentBidAmount = startBidAmount;
    private Double finalBidAmount;

    private Double bidIncrement = 0.1;

    @OneToOne
    private Item item;

    @OneToMany
    private List<Bid> bids;

    @OneToOne
    private User winner;

    @OneToMany
    private List<User> participants;
}
