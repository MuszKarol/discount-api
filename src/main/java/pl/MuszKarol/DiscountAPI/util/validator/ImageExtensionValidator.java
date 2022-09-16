package pl.MuszKarol.DiscountAPI.util.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.MuszKarol.DiscountAPI.exception.InvalidImageExtensionException;

import java.util.List;

@Component
public class ImageExtensionValidator {

    @Value("${image.extensions:jpg,png,gif,jpeg}")
    private List<String> extensions;

    public String run(String extension) throws InvalidImageExtensionException {
        final String modifiedExtension = extension.replace("image/", "");

        boolean present = extensions.stream().anyMatch(x -> x.equals(modifiedExtension.toLowerCase()));

        if (present) {
            return modifiedExtension.toLowerCase();
        } else {
            throw new InvalidImageExtensionException("Extension is not supported!");
        }
    }
}
