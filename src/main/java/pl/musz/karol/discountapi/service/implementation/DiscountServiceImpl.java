package pl.musz.karol.discountapi.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.musz.karol.discountapi.dto.DiscountDTORequest;
import pl.musz.karol.discountapi.dto.DiscountDTOResponse;
import pl.musz.karol.discountapi.dto.ImageIdentificationDetailsDTO;
import pl.musz.karol.discountapi.mapper.DiscountMapper;
import pl.musz.karol.discountapi.mapper.ImageMapper;
import pl.musz.karol.discountapi.model.Discount;
import pl.musz.karol.discountapi.model.Image;
import pl.musz.karol.discountapi.model.Product;
import pl.musz.karol.discountapi.repository.DiscountRepository;
import pl.musz.karol.discountapi.repository.ProductRepository;
import pl.musz.karol.discountapi.service.DiscountService;
import pl.musz.karol.discountapi.service.implementation.enums.DateType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Primary
@AllArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;
    private final ImageMapper imageMapper;

    @Override
    public Page<DiscountDTOResponse> getAllDiscounts(Pageable pageable, Optional<String> productName) {
        return discountRepository.findAllBy(pageable)
                .map(this::getDiscountDTOResponse);
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::getDiscountDTOResponse)
                .toList();
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth() {
        return getDiscounts(getDate(DateType.MONTH), getDate(DateType.NOW));
    }

    @Override
    public Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth(Pageable pageable, Optional<String> productName) {
        return productName
                .map(product -> getDiscount(getDate(DateType.MONTH), getDate(DateType.NOW), product, pageable))
                .orElseGet(() -> getDiscount(getDate(DateType.MONTH), getDate(DateType.NOW), pageable));
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek() {
        return getDiscounts(getDate(DateType.WEEK), getDate(DateType.NOW));
    }

    @Override
    public Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek(Pageable pageable, Optional<String> productName) {
        return productName
                .map(product -> getDiscount(getDate(DateType.WEEK), getDate(DateType.NOW), product, pageable))
                .orElseGet(() -> getDiscount(getDate(DateType.WEEK), getDate(DateType.NOW), pageable));
    }

    @Override
    public DiscountDTOResponse saveDiscount(DiscountDTORequest discountDTORequest) {
        Product product = getProduct(discountDTORequest);
        Discount discount = createDiscount(discountDTORequest, product);

        return getDiscountDTOResponse(discount);
    }

    private Date getDate(DateType dateType) {
        return switch (dateType) {
            case WEEK -> Date.valueOf(LocalDate.now().minusWeeks(1));
            case MONTH -> Date.valueOf(LocalDate.now().minusMonths(1));
            case NOW -> Date.valueOf(LocalDate.now());
        };
    }

    private DiscountDTOResponse getDiscountDTOResponse(Discount discount) {
        return discountMapper.discountToDiscountDTOResponse(discount,
                getImagesToListOfImageDTO(discount.getImages()));
    }

    private List<ImageIdentificationDetailsDTO> getImagesToListOfImageDTO(List<Image> images) {
        return images.stream()
                .map(imageMapper::imageToImageDTO)
                .toList();
    }

    private List<DiscountDTOResponse> getDiscounts(Date startDate, Date endDate) {
        return discountRepository.getDiscountByGivenDates(startDate, endDate)
                .stream()
                .map(this::getDiscountDTOResponse)
                .toList();
    }

    private Page<DiscountDTOResponse> getDiscount(Date startDate, Date endDate, String productName, Pageable pageable) {
        return discountRepository.getDiscountByGivenDates(startDate, endDate, productName, pageable)
                .map(this::getDiscountDTOResponse);
    }

    private Page<DiscountDTOResponse> getDiscount(Date startDate, Date endDate, Pageable pageable) {
        return discountRepository.getDiscountByGivenDates(startDate, endDate, pageable)
                .map(this::getDiscountDTOResponse);
    }

    private Discount createDiscount(DiscountDTORequest discountDTORequest, Product product) {
        Discount discount = Discount.builder()
                .discountEndDate(discountDTORequest.getDiscountEndDate())
                .discountStartDate(discountDTORequest.getDiscountStartDate())
                .url(discountDTORequest.getUrl())
                .basePrice(discountDTORequest.getBasePrice())
                .newPrice(discountDTORequest.getNewPrice())
                .likes(0)
                .dislikes(0)
                .product(product)
                .images(new LinkedList<>())
                .build();

        return discountRepository.save(discount);
    }

    private Product getProduct(DiscountDTORequest discountDTORequest) {
        Optional<Product> productOptional = productRepository.getProductByName(discountDTORequest.getProductName());

        return productOptional.isEmpty() ? createNewProduct(discountDTORequest) : productOptional.get();
    }

    private Product createNewProduct(DiscountDTORequest discountDTORequest) {
        Product product = Product.builder()
                .name(discountDTORequest.getProductName())
                .description(discountDTORequest.getDescription())
                .build();

        return productRepository.save(product);
    }
}
