package ca.jdelreyes.biddingbackend.service;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.exception.BidNotFoundException;

import java.util.List;

public interface BidService {
    List<BidResponse> getBids();

    BidResponse getBid(Integer id) throws BidNotFoundException;

    BidResponse createBid(Integer userId, BidRequest bidRequest) throws Exception;
}
