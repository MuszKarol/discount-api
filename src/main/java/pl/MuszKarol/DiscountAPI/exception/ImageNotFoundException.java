package pl.MuszKarol.DiscountAPI.exception;

import java.io.FileNotFoundException;

public class ImageNotFoundException extends FileNotFoundException {
    ImageNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
