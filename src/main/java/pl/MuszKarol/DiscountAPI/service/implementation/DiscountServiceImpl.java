package pl.MuszKarol.DiscountAPI.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.dto.ImageIdentificationDetailsDTO;
import pl.MuszKarol.DiscountAPI.mapper.DiscountMapper;
import pl.MuszKarol.DiscountAPI.mapper.ImageMapper;
import pl.MuszKarol.DiscountAPI.model.Discount;
import pl.MuszKarol.DiscountAPI.model.Image;
import pl.MuszKarol.DiscountAPI.model.Product;
import pl.MuszKarol.DiscountAPI.repository.DiscountRepository;
import pl.MuszKarol.DiscountAPI.repository.ProductRepository;
import pl.MuszKarol.DiscountAPI.service.DiscountService;
import pl.MuszKarol.DiscountAPI.service.implementation.enums.DateType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.MuszKarol.DiscountAPI.service.implementation.enums.DateType.*;

@Primary
@AllArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;
    private final ImageMapper imageMapper;

    @Override
    public Page<DiscountDTOResponse> getAllDiscounts(Pageable pageable) {
        return discountRepository.findAllBy(pageable)
                .map(this::getDiscountDTOResponse);
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::getDiscountDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth() {
        return getDiscountsByDate(getDate(MONTH), getDate(NOW));
    }

    @Override
    public Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth(Pageable pageable) {
        return this.getDiscountByDateAndPageable(getDate(MONTH), getDate(NOW), pageable);
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek() {
        return getDiscountsByDate(getDate(WEEK), getDate(NOW));
    }

    @Override
    public Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek(Pageable pageable) {
        return getDiscountByDateAndPageable(getDate(WEEK), getDate(NOW), pageable);
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
                .map(this.imageMapper::imageToImageDTO)
                .toList();
    }

    private List<DiscountDTOResponse> getDiscountsByDate(Date startDate, Date endDate) {
        return discountRepository.getDiscountByGivenDates(endDate, startDate)
                .stream()
                .map(this::getDiscountDTOResponse)
                .toList();
    }

    private Page<DiscountDTOResponse> getDiscountByDateAndPageable(Date startDate, Date endDate, Pageable pageable) {
        return discountRepository.getDiscountByGivenDates(endDate, startDate, pageable)
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
