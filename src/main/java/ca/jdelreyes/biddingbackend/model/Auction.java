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
    private LocalDateTime dateTimeStarted;
    private LocalDateTime dateTimeEnded;

    @OneToMany
    private List<Bid> bids;

    @OneToOne
    private User winner;

    @OneToMany
    private List<User> participants;
}
