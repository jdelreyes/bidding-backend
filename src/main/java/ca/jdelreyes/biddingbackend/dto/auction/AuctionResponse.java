package ca.jdelreyes.biddingbackend.dto.auction;

import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponse {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    private Status status;

    private ItemResponse item;

    private User winner;
}
