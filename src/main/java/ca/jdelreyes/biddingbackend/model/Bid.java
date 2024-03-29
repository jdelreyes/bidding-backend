package ca.jdelreyes.biddingbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;

    private LocalDateTime bidDate = LocalDateTime.now();

    @OneToOne
    private Item item;

    @ManyToOne
    private User bidder;

    @ManyToOne
    private Auction auction;
}
