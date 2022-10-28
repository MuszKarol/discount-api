package pl.MuszKarol.DiscountAPI.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.ImageDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.exception.InvalidImageExtensionException;
import pl.MuszKarol.DiscountAPI.model.Discount;
import pl.MuszKarol.DiscountAPI.model.Image;
import pl.MuszKarol.DiscountAPI.repository.DiscountRepository;
import pl.MuszKarol.DiscountAPI.repository.ImageRepository;
import pl.MuszKarol.DiscountAPI.service.ImageService;
import pl.MuszKarol.DiscountAPI.util.FileManager;
import pl.MuszKarol.DiscountAPI.util.validator.ImageExtensionValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageExtensionValidator imageExtensionValidator;
    private final ImageRepository imageRepository;
    private final DiscountRepository discountRepository;
    private final FileManager fileManager;

    @Value("${image.name.prefix:discount_image_}")
    private String imagePrefix;

    @Override
    public Resource getImageAsResource(String filename, String imageExtension)
            throws ImageNotFoundException, InvalidImageExtensionException {

        String extension = imageExtensionValidator.run(imageExtension);

        try {
            return fileManager.getImageAsInputStreamResource(filename, extension);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException("Image not found!");
        }
    }

    @Override
    public List<ImageDTOResponse> saveImages(List<MultipartFile> multipartFileList, UUID discountId)
            throws IOException, InvalidImageExtensionException {

        List<ImageDTOResponse> responseList = new LinkedList<>();
        Optional<Discount> discountOptional = discountRepository.findById(discountId);

        if (discountOptional.isEmpty()) {
            throw new IOException("Discount not found");
        }

        Discount discount = discountOptional.get();

        processImages(multipartFileList, discountId, responseList, discount);

        discountRepository.save(discount);

        return responseList;
    }

    private void processImages(List<MultipartFile> multipartFileList,
                               UUID discountId,
                               List<ImageDTOResponse> responseList,
                               Discount discount) throws IOException, InvalidImageExtensionException {

        List<Image> newImages = discount.getImages();

        for (MultipartFile multipartFile : multipartFileList) {
            newImages.add(saveImage(multipartFile, discountId));
            responseList.add(createImageDTOResponse(discountId));
        }
    }

    private ImageDTOResponse createImageDTOResponse(UUID discountId) {
        return ImageDTOResponse.builder()
                .discountId(discountId)
                .imageName(imagePrefix + discountId)
                .saved(true)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    private Image saveImage(MultipartFile image, UUID uuid) throws IOException, InvalidImageExtensionException {
        String extension = imageExtensionValidator.run(getImageContentType(image));
        String imageName = buildNewImageName(uuid);

        File file = fileManager.getNewFile(imageName, extension);

        image.transferTo(file);

        return imageRepository.save(new Image(imageName, extension));
    }

    private String buildNewImageName(UUID uuid) {
        return imagePrefix + uuid + imageRepository.count();
    }

    private String getImageContentType(MultipartFile image) throws InvalidImageExtensionException {
        String contentType = image.getContentType();

        if (contentType == null) {
            throw new InvalidImageExtensionException("No content type!");
        }
        return contentType;
    }
}
