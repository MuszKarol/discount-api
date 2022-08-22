package pl.MuszKarol.DiscountAPI.service.implementation;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.mapper.DiscountMapper;
import pl.MuszKarol.DiscountAPI.model.Discount;
import pl.MuszKarol.DiscountAPI.model.Product;
import pl.MuszKarol.DiscountAPI.repository.DiscountRepository;
import pl.MuszKarol.DiscountAPI.repository.ProductRepository;
import pl.MuszKarol.DiscountAPI.service.DiscountService;
import pl.MuszKarol.DiscountAPI.util.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;
    private final FileManager fileManager;

    @Override
    public List<DiscountDTOResponse> takeDiscountsByDate(Date startDate, Date endDate) {
        return discountRepository.getDiscountsByDiscountEndDateLessThanAndDiscountStartDateGreaterThan(endDate, startDate)
                .stream()
                .map(discountMapper::discountToDiscountDTOResponse)
                .toList();
    }

    @Override
    public DiscountDTOResponse saveDiscount(MultipartFile image, DiscountDTORequest discountDTORequest) throws FileUploadException {
        Product product = getProduct(discountDTORequest);
        Discount discount = createDiscount(discountDTORequest, product);

        try {
            saveImage(image, discount, discountDTORequest.imageExtension);
        } catch (IOException e) {
            throw new FileUploadException();
        }

        return discountMapper.discountToDiscountDTOResponse(discount);
    }

    @Override
    public Resource getImageAsResource(String filename, String imageExtension) throws ImageNotFoundException {
        try {
            return fileManager.getImageAsInputStreamResource(filename, imageExtension);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException("Image not found!");
        }
    }

    private void saveImage(MultipartFile image, Discount discount, String imageExtension) throws IOException {
        File file = fileManager.getNewFile("discount_image_" + discount.getId(), imageExtension);
        image.transferTo(file);
    }

    private Discount createDiscount(DiscountDTORequest discountDTORequest, Product product) {
        Discount discount = Discount.builder()
                .discountEndDate(discountDTORequest.discountEndDate)
                .discountStartDate(discountDTORequest.discountStartDate)
                .url(discountDTORequest.url)
                .basePrice(discountDTORequest.basePrice)
                .newPrice(discountDTORequest.newPrice)
                .likes(0)
                .dislikes(0)
                .product(product)
                .build();

        return discountRepository.save(discount);
    }

    private Product getProduct(DiscountDTORequest discountDTORequest) {
        Optional<Product> productOptional = productRepository.getProductByName(discountDTORequest.productName);

        return productOptional.isEmpty() ? createNewProduct(discountDTORequest) : productOptional.get();
    }

    private Product createNewProduct(DiscountDTORequest discountDTORequest) {
        Product product = Product.builder()
                .name(discountDTORequest.productName)
                .description(discountDTORequest.description)
                .build();

        return productRepository.save(product);
    }

}
