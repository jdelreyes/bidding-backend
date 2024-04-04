package ca.jdelreyes.biddingbackend.service.auction;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.repository.AuctionRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<AuctionResponse> getAuctions() {
        return auctionRepository.findAll().stream().map(this::mapAuctionToAuctionResponse).collect(Collectors.toList());
    }

    @Override
    public AuctionResponse auctionItem(String userName, AuctionRequest auctionRequest) throws Exception {
        Item item = itemRepository.findItemById(auctionRequest.getItemId()).orElseThrow(ItemNotFoundException::new);

        if (!Objects.equals(item.getSeller().getEmail(), userName))
            throw new Exception();

        Auction auction = Auction.builder()
                .item(item)
                .startAt(auctionRequest.getStartAt())
                .endAt(auctionRequest.getEndAt())
                .build();

        auctionRepository.save(auction);

        return mapAuctionToAuctionResponse(auction);

    }

    private AuctionResponse mapAuctionToAuctionResponse(Auction auction) {
        return AuctionResponse.builder()
                .id(auction.getId())
                .startAt(auction.getStartAt())
                .endAt(auction.getEndAt())
                .bids(auction.getBids())
                .winner(auction.getWinner())
                .build();
    }
}
