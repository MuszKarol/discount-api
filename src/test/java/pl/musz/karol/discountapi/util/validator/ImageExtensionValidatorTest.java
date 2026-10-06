package pl.musz.karol.discountapi.util.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;


class ImageExtensionValidatorTest {

    private final ImageExtensionValidator imageExtensionValidator = new ImageExtensionValidator(List.of("png", "jpeg", "gif"));

    @ParameterizedTest
    @ValueSource(strings = {"jpeg", "gif", "png"})
    void validExtensionTest(String extension) {
        ValidationStatus result = imageExtensionValidator.run(extension);
        Assertions.assertEquals(ValidationStatus.VALID, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"jpg", "Gif", ".png"})
    void invalidExtensionTest(String extension) {
        ValidationStatus result = imageExtensionValidator.run(extension);
        Assertions.assertEquals(ValidationStatus.INVALID, result);
    }
}