package ca.jdelreyes.biddingbackend.events;

import org.springframework.context.ApplicationEvent;

public class BidEvent extends ApplicationEvent {
    public BidEvent(Object source) {
        super(source);
    }
}
