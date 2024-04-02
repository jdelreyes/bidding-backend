package ca.jdelreyes.biddingbackend.service.bid;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;

import java.util.List;

public interface BidService {
    List<BidResponse> getBids();

    BidResponse bid(String userName, BidRequest bidRequest) throws Exception;
}
