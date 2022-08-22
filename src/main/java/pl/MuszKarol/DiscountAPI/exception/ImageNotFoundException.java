package pl.MuszKarol.DiscountAPI.exception;

import java.io.FileNotFoundException;

public class ImageNotFoundException extends FileNotFoundException {
    public ImageNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
