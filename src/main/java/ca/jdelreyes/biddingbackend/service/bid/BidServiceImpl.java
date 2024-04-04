package ca.jdelreyes.biddingbackend.service.bid;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.exception.AuctionNotFoundException;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Bid;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.AuctionRepository;
import ca.jdelreyes.biddingbackend.repository.BidRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public List<BidResponse> getBids() {
        return bidRepository
                .findAll()
                .stream()
                .map(this::mapBidToBidResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BidResponse bid(String userName, BidRequest bidRequest) throws Exception {
        Auction auction = auctionRepository.findAuctionById(bidRequest.getAuctionId())
                .orElseThrow(AuctionNotFoundException::new);
        User user = userRepository.findUserByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist"));

        if (Objects.equals(user.getId(), auction.getItem().getSeller().getId()))
            throw new Exception("item is not to be bid by seller");

        Double amount = auction.getItem().getCurrentBidAmount() + auction.getItem().getBidIncrement();

        BigDecimal bigDecimal = new BigDecimal(Double.toString(amount));
        bigDecimal.setScale(2, RoundingMode.HALF_UP);

        Bid bid = Bid.builder()
                .amount(bigDecimal.doubleValue())
                .bidder(user)
                .auction(auction)
                .build();

        Item item = auction.getItem();

        item.setCurrentBidAmount(bigDecimal.doubleValue());

        itemRepository.save(item);
        bidRepository.save(bid);
        auctionRepository.save(auction);

        return mapBidToBidResponse(bid);
    }

    private BidResponse mapBidToBidResponse(Bid bid) {
        return BidResponse.builder()
                .id(bid.getId())
                .amount(bid.getAmount())
                .auction(mapAuctionToAuctionResponse(bid.getAuction()))
                .build();
    }

    private AuctionResponse mapAuctionToAuctionResponse(Auction auction) {
        return AuctionResponse.builder()
                .id(auction.getId())
                .startAt(auction.getStartAt())
                .endAt(auction.getEndAt())
                .winner(auction.getWinner())
                .build();
    }
}
