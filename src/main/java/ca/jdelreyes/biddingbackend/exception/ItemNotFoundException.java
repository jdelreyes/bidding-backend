package ca.jdelreyes.biddingbackend.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
        super("Item is not found");
    }
}
