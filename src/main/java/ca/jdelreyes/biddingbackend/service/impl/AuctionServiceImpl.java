package ca.jdelreyes.biddingbackend.service.impl;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.dto.auction.CreateAuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.UpdateAuctionRequest;
import ca.jdelreyes.biddingbackend.exception.AuctionNotFoundException;
import ca.jdelreyes.biddingbackend.exception.ItemNotFoundException;
import ca.jdelreyes.biddingbackend.mapper.Mapper;
import ca.jdelreyes.biddingbackend.model.Auction;
import ca.jdelreyes.biddingbackend.model.Item;
import ca.jdelreyes.biddingbackend.repository.AuctionRepository;
import ca.jdelreyes.biddingbackend.repository.ItemRepository;
import ca.jdelreyes.biddingbackend.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static ca.jdelreyes.biddingbackend.mapper.Mapper.mapAuctionToAuctionResponse;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<AuctionResponse> getAuctions() {
        return auctionRepository.findAll().stream().map(Mapper::mapAuctionToAuctionResponse).collect(Collectors.toList());
    }

    @Override
    public AuctionResponse getAuction(Integer id) throws AuctionNotFoundException {
        return mapAuctionToAuctionResponse(
                auctionRepository.findAuctionById(id).orElseThrow(AuctionNotFoundException::new));
    }

    @Override
    public AuctionResponse createAuction(Integer userId, CreateAuctionRequest createAuctionRequest) throws Exception {
        Item item = itemRepository.findItemById(createAuctionRequest.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        if (item.getSeller().getId() != userId)
            throw new Exception("Item is not owned by current user");

        Auction auction = Auction.builder()
                .item(item)
                .startAt(createAuctionRequest.getStartAt())
                .endAt(createAuctionRequest.getEndAt())
                .build();

        auctionRepository.save(auction);

        return mapAuctionToAuctionResponse(auction);

    }

    @Override
    public AuctionResponse updateAuction(Integer id, UpdateAuctionRequest updateAuctionRequest) throws Exception {
        Auction auction = auctionRepository.findAuctionById(id).orElseThrow(AuctionNotFoundException::new);

        auction.setStartAt(updateAuctionRequest.getStartAt());
        auction.setEndAt(updateAuctionRequest.getEndAt());

        return mapAuctionToAuctionResponse(auction);
    }

    @Override
    public AuctionResponse deleteAuction(Integer id) throws Exception {
        Auction auction = auctionRepository.findAuctionById(id).orElseThrow(AuctionNotFoundException::new);

        if (auctionRepository.existsById(id))
            throw new Exception("auction delete failed");

        return mapAuctionToAuctionResponse(auction);
    }


}
