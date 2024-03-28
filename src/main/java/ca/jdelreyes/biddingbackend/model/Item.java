package ca.jdelreyes.biddingbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private double startBidAmount;
    private double bidIncrement;
    @Builder.Default
    private LocalDateTime dateTimeCreated = LocalDateTime.now();

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Auction auction;

}
