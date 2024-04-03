package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.auction.AuctionRequest;
import ca.jdelreyes.biddingbackend.dto.auction.AuctionResponse;
import ca.jdelreyes.biddingbackend.service.auction.AuctionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<AuctionResponse> auctionItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AuctionRequest auctionRequest) throws Exception {
        return new ResponseEntity<>(auctionService.auctionItem(userDetails.getUsername(), auctionRequest),
                HttpStatus.CREATED);
    }
}
