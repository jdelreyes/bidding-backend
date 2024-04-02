package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.service.bid.BidServiceImpl;
import jakarta.validation.Valid;
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
@RequestMapping("/api/bids")
@RequiredArgsConstructor
@EnableMethodSecurity
public class BidController {
    private final BidServiceImpl bidService;

    @GetMapping
    public ResponseEntity<List<BidResponse>> getBids() {
        return ResponseEntity.ok(bidService.getBids());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<BidResponse> bid(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody BidRequest bidRequest) throws Exception {
        return new ResponseEntity<>(bidService.bid(userDetails.getUsername(), bidRequest), HttpStatus.CREATED);
    }
}
