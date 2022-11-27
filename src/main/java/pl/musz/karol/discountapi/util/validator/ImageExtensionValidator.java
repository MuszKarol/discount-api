package pl.musz.karol.discountapi.util.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageExtensionValidator {

    private final List<String> extensions;

    ImageExtensionValidator(@Value("${image.extensions:png,gif,jpeg}") List<String> extensions) {
        this.extensions = extensions;
    }

    public ValidationStatus run(String extensionToCompare) {
        boolean present = extensions.stream()
                .anyMatch(extension -> extension.equals(extensionToCompare));

        return present ? ValidationStatus.VALID : ValidationStatus.INVALID;
    }
}
