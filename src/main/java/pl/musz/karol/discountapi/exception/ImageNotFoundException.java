package pl.musz.karol.discountapi.exception;

import java.io.FileNotFoundException;

public class ImageNotFoundException extends FileNotFoundException {
    public ImageNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
