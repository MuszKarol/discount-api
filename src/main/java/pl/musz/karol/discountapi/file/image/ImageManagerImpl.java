package pl.musz.karol.discountapi.file.image;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.musz.karol.discountapi.exception.ImageNotFoundException;
import pl.musz.karol.discountapi.exception.InvalidImageExtensionException;
import pl.musz.karol.discountapi.file.FileHandler;
import pl.musz.karol.discountapi.model.Image;
import pl.musz.karol.discountapi.util.formatter.ImageExtensionFormatter;
import pl.musz.karol.discountapi.util.validator.ImageExtensionValidator;
import pl.musz.karol.discountapi.util.validator.ValidationStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ImageManagerImpl implements ImageManager {

    private final FileHandler fileHandler;
    private final DigestUtils digestUtils;
    private final ImageExtensionValidator imageExtensionValidator;

    @Value("${image.name.prefix:discount_image_}")
    private String imagePrefix;

    @Override
    public InputStreamResource getImage(String imageName, String extension)
            throws ImageNotFoundException, InvalidImageExtensionException  {

        String formattedExtension = ImageExtensionFormatter.formatExtension(extension);
        ValidationStatus validationStatus = imageExtensionValidator.run(formattedExtension);

        if (validationStatus.equals(ValidationStatus.INVALID)) {
            throw new InvalidImageExtensionException("Extension is not supported!");
        }

        try {

            return fileHandler.getFileAsInputStreamResource(imageName, formattedExtension);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException("Image not found!");
        }
    }

    @Override
    public List<Image> saveImages(List<MultipartFile> images, UUID discountId, Integer numberOfImages)
            throws InvalidImageExtensionException, IOException {

        List<Image> imageList = new LinkedList<>();

        for (MultipartFile multipartFile : images) {
            numberOfImages = processImage(discountId, numberOfImages, imageList, multipartFile);
        }

        return imageList;
    }

    private Integer processImage(UUID discountId, Integer numberOfImages, List<Image> imageList,
                                 MultipartFile multipartFile) throws InvalidImageExtensionException, IOException {

        String imageExtension = getImageContentType(multipartFile);
        Image image = buildImage(discountId, numberOfImages, imageExtension);
        saveToDisk(multipartFile, image);
        imageList.add(image);
        numberOfImages += 1;
        return numberOfImages;
    }

    private Image buildImage(UUID discountId, Integer numberOfImages, String imageExtension) {
        return Image.builder()
                .imageName(generateImageName(discountId, numberOfImages))
                .extension(imageExtension)
                .build();
    }

    private void saveToDisk(MultipartFile multipartFile, Image image) throws IOException {
        File file = fileHandler.getNewFile(image.getImageName(), image.getExtension());
        multipartFile.transferTo(file);
    }

    private String generateImageName(UUID discountId, Integer numberOfImages) {
        return digestUtils.digestAsHex(imagePrefix + discountId + numberOfImages);
    }

    private String getImageContentType(MultipartFile image) throws InvalidImageExtensionException {
        String contentType = image.getContentType();

        if (contentType == null) {
            throw new InvalidImageExtensionException("No content type!");
        }

        return ImageExtensionFormatter.formatExtension(contentType);
    }
}
