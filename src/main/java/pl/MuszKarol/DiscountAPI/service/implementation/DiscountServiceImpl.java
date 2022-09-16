package pl.MuszKarol.DiscountAPI.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.mapper.DiscountMapper;
import pl.MuszKarol.DiscountAPI.model.Discount;
import pl.MuszKarol.DiscountAPI.model.Product;
import pl.MuszKarol.DiscountAPI.repository.DiscountRepository;
import pl.MuszKarol.DiscountAPI.repository.ProductRepository;
import pl.MuszKarol.DiscountAPI.service.DiscountService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth() {
        return getDiscountsByDate(
                Date.valueOf(LocalDate.now().minusMonths(1)),
                Date.valueOf(LocalDate.now())
        );
    }

    @Override
    public List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek() {
        return getDiscountsByDate(
                Date.valueOf(LocalDate.now().minusWeeks(1)),
                Date.valueOf(LocalDate.now())
        );
    }

    @Override
    public DiscountDTOResponse saveDiscount(DiscountDTORequest discountDTORequest) {

        Product product = getProduct(discountDTORequest);
        Discount discount = createDiscount(discountDTORequest, product);

        return discountMapper.discountToDiscountDTOResponse(discount);
    }

    private List<DiscountDTOResponse> getDiscountsByDate(Date startDate, Date endDate) {
        return discountRepository.getDiscountsByDiscountEndDateLessThanAndDiscountStartDateGreaterThan(endDate, startDate)
                .stream()
                .map(discountMapper::discountToDiscountDTOResponse)
                .toList();
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
