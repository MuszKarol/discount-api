package pl.musz.karol.discountapi.file.image;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import pl.musz.karol.discountapi.exception.ImageNotFoundException;
import pl.musz.karol.discountapi.exception.InvalidImageExtensionException;
import pl.musz.karol.discountapi.model.Image;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageManager {
    InputStreamResource getImage(String imageName, String extension) throws ImageNotFoundException, InvalidImageExtensionException;

    List<Image> saveImages(List<MultipartFile> images, UUID discountId, Integer numberOfImages) throws InvalidImageExtensionException, IOException;
}
