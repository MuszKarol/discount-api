package pl.MuszKarol.DiscountAPI.util.validator;

import org.springframework.stereotype.Component;
import pl.MuszKarol.DiscountAPI.exception.InvalidExtensionException;

import java.util.List;

@Component
public class ImageExtensionValidator {

    private final List<String> extensions;

    public ImageExtensionValidator() {
        this.extensions = List.of("jpg", "png", "gif");
    }

    public String run(String extension) throws InvalidExtensionException {
        boolean present = extensions.stream().anyMatch(x -> x.equals(extension.toLowerCase()));

        if (present) {
            return extension.toLowerCase();
        } else {
            throw new InvalidExtensionException("Extension is not supported!");
        }
    }
}
