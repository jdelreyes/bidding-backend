package ca.jdelreyes.biddingbackend.service.auction;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;

import java.util.List;

public interface AuctionService {
    List<AuctionResponse> getAuctions();

    AuctionResponse auctionItem(String userName, AuctionRequest auctionRequest) throws Exception;
}
