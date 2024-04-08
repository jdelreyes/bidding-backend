package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.dto.auction.CreateAuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.UpdateAuctionRequest;
import ca.jdelreyes.biddingbackend.exception.AuctionNotFoundException;

import java.util.List;

public interface AuctionService {
    List<AuctionResponse> getAuctions();

    AuctionResponse getAuction(Integer id) throws AuctionNotFoundException;

    AuctionResponse createAuction(Integer userId, CreateAuctionRequest createAuctionRequest) throws Exception;

    AuctionResponse updateAuction(Integer id, UpdateAuctionRequest updateAuctionRequest) throws Exception;

    AuctionResponse deleteAuction(Integer id) throws Exception;
}
