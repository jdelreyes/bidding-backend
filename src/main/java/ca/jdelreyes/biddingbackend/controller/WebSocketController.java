package ca.jdelreyes.biddingbackend.controller;

import ca.jdelreyes.biddingbackend.dto.bid.BidResponse;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @SendTo("/topic/bids")
    public BidResponse sendBids(BidResponse bidResponse) {
        return bidResponse;
    }
}
