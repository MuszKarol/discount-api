package pl.MuszKarol.DiscountAPI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.ImageDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.exception.InvalidImageExtensionException;
import pl.MuszKarol.DiscountAPI.service.ImageService;
import pl.MuszKarol.DiscountAPI.util.FileManager;
import pl.MuszKarol.DiscountAPI.util.validator.ImageExtensionValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageExtensionValidator imageExtensionValidator;
    private final FileManager fileManager;

    @Value("${image.name.prefix:discount_image_}")
    private String imagePrefix;

    @Override
    public Resource getImageAsResource(String filename, String imageExtension) throws ImageNotFoundException, InvalidImageExtensionException {
        String extension = imageExtensionValidator.run(imageExtension);

        try {
            return fileManager.getImageAsInputStreamResource(filename, extension);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException("Image not found!");
        }
    }

    @Override
    public List<ImageDTOResponse> saveImages(List<MultipartFile> multipartFileList, UUID discountId) throws IOException, InvalidImageExtensionException {
        List<ImageDTOResponse> responseList = new LinkedList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            saveImage(multipartFile, discountId);
            responseList.add(createImageDTOResponse(discountId));
        }

        return responseList;
    }

    private ImageDTOResponse createImageDTOResponse(UUID discountId) {
        return ImageDTOResponse.builder()
                .discountId(discountId)
                .imageName(imagePrefix + discountId)
                .saved(true)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    private void saveImage(MultipartFile image, UUID uuid) throws IOException, InvalidImageExtensionException {
        String extension = imageExtensionValidator.run(getImageContentType(image));

        File file = fileManager.getNewFile(imagePrefix + uuid, extension);
        image.transferTo(file);
    }

    private String getImageContentType(MultipartFile image) throws InvalidImageExtensionException {
        String contentType = image.getContentType();

        if (contentType == null) {
            throw new InvalidImageExtensionException("No content type!");
        }
        return contentType;
    }
}
