package ca.jdelreyes.biddingbackend.service.bid;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Bid;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.AuctionRepository;
import ca.jdelreyes.biddingbackend.repository.BidRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        Auction auction = auctionRepository.findAuctionById(bidRequest.getAuctionId()).orElseThrow();


        User user = userRepository.findUserByEmail(userName).orElseThrow(() -> new Exception("user not found"));
        Item item = itemRepository.findItemById(auction.getItem().getId()).orElseThrow(() -> new Exception("item not found"));

        Bid bid = Bid.builder()
                .bidder(user)
                .item(item)
                .build();

//        add current bid to the auction it belongs to.
        auction.getBids().add(bid);

        auctionRepository.save(auction);
        bidRepository.save(bid);

        return mapBidToBidResponse(bid);
    }

    private BidResponse mapBidToBidResponse(Bid bid) {
        return BidResponse.builder()
                .id(bid.getId())
                .amount(bid.getAmount())
                .item(bid.getItem())
                .build();
    }
}
