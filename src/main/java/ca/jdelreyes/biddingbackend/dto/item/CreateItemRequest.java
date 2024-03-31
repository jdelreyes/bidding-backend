package ca.jdelreyes.biddingbackend.dto.item;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Double startBidAmount;
    @NotNull
    private Double finalBidAmount;
    @NotNull
    private Double bidIncrement;
    @NotNull
    private Integer categoryId;
}
