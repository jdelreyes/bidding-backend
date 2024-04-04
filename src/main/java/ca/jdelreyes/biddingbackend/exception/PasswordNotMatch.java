package ca.jdelreyes.biddingbackend.exception;

public class PasswordNotMatch extends Exception {
    public PasswordNotMatch() {
        super("Password does not match");
    }
}
