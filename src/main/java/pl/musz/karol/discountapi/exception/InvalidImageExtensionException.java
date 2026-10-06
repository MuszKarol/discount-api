package pl.musz.karol.discountapi.exception;

public class InvalidImageExtensionException extends Exception {
    public InvalidImageExtensionException(String errorMessage) {
        super(errorMessage);
    }
}
