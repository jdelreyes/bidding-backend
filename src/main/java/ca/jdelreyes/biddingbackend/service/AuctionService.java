package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;

import java.util.List;

public interface AuctionService {
    List<AuctionResponse> getAuctions();

    AuctionResponse getAuction();

    AuctionResponse createAuction(String userName, AuctionRequest auctionRequest);
}
