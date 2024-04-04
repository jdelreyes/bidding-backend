package ca.jdelreyes.biddingbackend.exception;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("category is not found");
    }
}
