package ca.jdelreyes.biddingbackend.exception;

public class BidNotFoundException extends Exception {
    public BidNotFoundException() {
        super("bid is not found");
    }
}
