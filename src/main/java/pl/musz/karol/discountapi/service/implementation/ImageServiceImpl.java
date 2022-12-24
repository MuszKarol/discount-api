package pl.musz.karol.discountapi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.musz.karol.discountapi.dto.ImageDTOResponse;
import pl.musz.karol.discountapi.exception.ImageNotFoundException;
import pl.musz.karol.discountapi.exception.InvalidImageExtensionException;
import pl.musz.karol.discountapi.file.image.ImageManager;
import pl.musz.karol.discountapi.model.Discount;
import pl.musz.karol.discountapi.model.Image;
import pl.musz.karol.discountapi.repository.DiscountRepository;
import pl.musz.karol.discountapi.repository.ImageRepository;
import pl.musz.karol.discountapi.service.ImageService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final DiscountRepository discountRepository;
    private final ImageManager imageManager;


    @Override
    public Resource getImageAsResource(String filename, String imageExtension)
            throws ImageNotFoundException, InvalidImageExtensionException {
        return imageManager.getImage(filename, imageExtension);
    }

    @Override
    public List<ImageDTOResponse> saveAllImages(List<MultipartFile> multipartFileList, UUID discountId)
            throws IOException, InvalidImageExtensionException {
        Discount discount = findDiscount(discountId);
        List<Image> images = imageManager.saveImages(multipartFileList, discount.getId(), discount.getImages().size());
        save(discount, images);

        return getResponseList(discountId, images);
    }

    private List<ImageDTOResponse> getResponseList(UUID discountId, List<Image> images) {
        return images.stream()
                .map(image -> createImageDTOResponse(discountId, image))
                .toList();
    }

    private void save(Discount discount, List<Image> images) {
        imageRepository.saveAll(images);
        discount.getImages().addAll(images);
        discountRepository.save(discount);
    }

    private Discount findDiscount(UUID discountId) throws IOException {
        Optional<Discount> discountOptional = discountRepository.findById(discountId);

        if (discountOptional.isEmpty()) {
            throw new IOException("Discount not found");
        }

        return discountOptional.get();
    }

    private ImageDTOResponse createImageDTOResponse(UUID discountId, Image image) {
        return ImageDTOResponse.builder()
                .discountId(discountId)
                .imageName(image.getImageName())
                .imageExtension(image.getExtension())
                .saved(true)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
