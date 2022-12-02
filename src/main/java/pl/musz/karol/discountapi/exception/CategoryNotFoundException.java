package pl.musz.karol.discountapi.exception;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}