package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.dto.auction.CreateAuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.UpdateAuctionRequest;
import ca.jdelreyes.biddingbackend.exception.AuctionNotFoundException;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.service.impl.AuctionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuctionController {
    private final AuctionServiceImpl auctionService;

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAuctions() {
        return ResponseEntity.ok(auctionService.getAuctions());
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionResponse> getAuction(@PathVariable("auctionId") Integer id)
            throws AuctionNotFoundException {
        return ResponseEntity.ok(auctionService.getAuction(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")

    public ResponseEntity<AuctionResponse> auctionItem(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateAuctionRequest createAuctionRequest) throws Exception {
        return new ResponseEntity<>(auctionService.createAuction(user.getId(), createAuctionRequest),
                HttpStatus.CREATED);
    }


    @PutMapping("/{auctionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuctionResponse> updateAuction(@PathVariable("auctionId") Integer id,
                                                         @Valid @RequestBody UpdateAuctionRequest updateAuctionRequest)
            throws Exception {
        return new ResponseEntity<>(auctionService.updateAuction(id, updateAuctionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{auctionId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteAuction(@PathVariable("auctionId") Integer id) throws Exception {
        auctionService.deleteAuction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
