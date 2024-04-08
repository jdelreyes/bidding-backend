package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.bid.BidRequest;
import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import ca.jdelreyes.biddingbackend.exception.BidNotFoundException;
import ca.jdelreyes.biddingbackend.model.User;
import ca.jdelreyes.biddingbackend.service.impl.BidServiceImpl;
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
@RequestMapping("/api/bids")
@RequiredArgsConstructor
@EnableMethodSecurity
public class BidController {
    private final BidServiceImpl bidService;

    @GetMapping
    public ResponseEntity<List<BidResponse>> getBids() {
        return ResponseEntity.ok(bidService.getBids());
    }

    @GetMapping("/{bidId}")
    public ResponseEntity<BidResponse> getBid(@PathVariable("bidId") Integer id) throws BidNotFoundException {
        return new ResponseEntity<>(bidService.getBid(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<BidResponse> bid(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody BidRequest bidRequest) throws Exception {
        return new ResponseEntity<>(bidService.createBid(user.getId(), bidRequest), HttpStatus.CREATED);
    }
}
