package ca.jdelreyes.biddingbackend.mapper;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.dto.item.ItemResponse;
import ca.jdelreyes.biddingbackend.dto.user.UserResponse;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Bid;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.model.User;

public class Mapper {
    private Mapper() {
    }

    public static UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .joinedAt(user.getJoinedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole())
                .build();
    }

    public static ItemResponse mapItemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .createdAt(item.getCreatedAt())
                .startBidAmount(item.getStartBidAmount())
                .finalBidAmount(item.getFinalBidAmount())
                .currentBidAmount(item.getCurrentBidAmount())
                .bidIncrement(item.getBidIncrement())
                .seller(mapUserToUserResponse(item.getSeller()))
                .category(item.getCategory())
                .build();
    }

    public static AuctionResponse mapAuctionToAuctionResponse(Auction auction) {
        return AuctionResponse.builder()
                .id(auction.getId())
                .startAt(auction.getStartAt())
                .endAt(auction.getEndAt())
                .item(mapItemToItemResponse(auction.getItem()))
                .winner(auction.getWinner())
                .build();
    }

    public static BidResponse mapBidToBidResponse(Bid bid) {
        return BidResponse.builder()
                .id(bid.getId())
                .amount(bid.getAmount())
                .bidAt(bid.getBidAt())
                .auction(mapAuctionToAuctionResponse(bid.getAuction()))
                .build();
    }
}
