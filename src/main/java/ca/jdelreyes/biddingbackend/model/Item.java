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

    private Double startBidAmount;
    private Double finalBidAmount;

    private Double bidIncrement = 0.1;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Category category;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User seller;

}
