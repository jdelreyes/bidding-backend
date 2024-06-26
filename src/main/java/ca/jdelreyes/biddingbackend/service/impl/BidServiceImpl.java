package ca.jdelreyes.biddingbackend.service.impl;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.exception.AuctionNotFoundException;
import ca.jdelreyes.biddingbackend.exception.BidNotFoundException;
import ca.jdelreyes.biddingbackend.mapper.Mapper;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Bid;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.repository.AuctionRepository;
import ca.jdelreyes.biddingbackend.repository.BidRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.repository.UserRepository;
import ca.jdelreyes.biddingbackend.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ca.jdelreyes.biddingbackend.mapper.Mapper.mapBidToBidResponse;

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
                .map(Mapper::mapBidToBidResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BidResponse getBid(Integer id) throws BidNotFoundException {
        return mapBidToBidResponse(bidRepository.findById(id).orElseThrow(BidNotFoundException::new));
    }

    @Override
    public BidResponse createBid(Integer userId, BidRequest bidRequest) throws Exception {
        Auction auction = auctionRepository.findAuctionById(bidRequest.getAuctionId())
                .orElseThrow(AuctionNotFoundException::new);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user does not exist"));

        if (isCurrentUserSeller(user, auction))
            throw new Exception("Item is not to be bid by seller");

        if (isItemInAuction(auction))
            throw new Exception("Item is not in auction");

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

    private boolean isItemInAuction(Auction auction) {
        return auction.getStartAt().isBefore(LocalDateTime.now()) && auction.getEndAt().isAfter(LocalDateTime.now());
    }

    private boolean isCurrentUserSeller(User user, Auction auction) {
        return Objects.equals(user.getId(), auction.getItem().getSeller().getId());
    }

}
