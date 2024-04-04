package ca.jdelreyes.biddingbackend.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("user is not found");
    }
}
