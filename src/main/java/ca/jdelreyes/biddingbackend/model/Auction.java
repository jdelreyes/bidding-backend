package ca.jdelreyes.biddingbackend.model;

import ca.jdelreyes.biddingbackend.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Item item;

    @OneToOne
    private User winner;

    @PrePersist
    private void prePersist() {
        if (startAt.isAfter(endAt))
            throw new RuntimeException("Auction start time is after end time");
    }
}
