package pl.musz.karol.discountapi.util.validator;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;


public class ImageExtensionValidatorTest {

    private final ImageExtensionValidator imageExtensionValidator = new ImageExtensionValidator(List.of("png", "jpeg", "gif"));

    @Test
    public void validateJpegExtensionTest() {
        String extension = "jpeg";

        ValidationStatus result = imageExtensionValidator.run(extension);

        assertEquals(ValidationStatus.VALID, result);
    }

    @Test
    public void validateGifExtensionTest() {
        String extension = "gif";

        ValidationStatus result = imageExtensionValidator.run(extension);

        assertEquals(ValidationStatus.VALID, result);
    }

    @Test
    public void validatePngExtensionTest() {
        String extension = "png";

        ValidationStatus result = imageExtensionValidator.run(extension);

        assertEquals(ValidationStatus.VALID, result);
    }
}