package pl.MuszKarol.DiscountAPI.exception;

public class InvalidImageExtensionException extends Exception {
    public InvalidImageExtensionException(String errorMessage) {
        super(errorMessage);
    }
}
