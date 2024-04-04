package ca.jdelreyes.biddingbackend.exception;

public class AuctionNotFoundException extends Exception{
    public AuctionNotFoundException() {
        super("auction is not found");
    }
}
