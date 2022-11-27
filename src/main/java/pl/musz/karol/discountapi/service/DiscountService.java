package pl.musz.karol.discountapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.musz.karol.discountapi.dto.DiscountDTORequest;
import pl.musz.karol.discountapi.dto.DiscountDTOResponse;

import java.util.List;
import java.util.Optional;

public interface DiscountService {
    /**
     * @deprecated
     * This method has been replaced by a method that uses paging to return results
     * <p> Use {@link DiscountService#getAllDiscounts(Pageable, Optional)} instead.
     */
    @Deprecated(since = "0.0.1-SNAPSHOT")
    List<DiscountDTOResponse> getAllDiscounts();

    Page<DiscountDTOResponse> getAllDiscounts(Pageable pageable, Optional<String> productName);

    /**
     * @deprecated
     * This method has been replaced by a method that uses paging to return results
     * <p> Use {@link DiscountService#getAllDiscountsOfTheCurrentMonth(Pageable, Optional)} instead.
     */
    @Deprecated(since = "0.0.1-SNAPSHOT")
    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth();

    Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth(Pageable pageable, Optional<String> productName);

    /**
     * @deprecated
     * This method has been replaced by a method that uses paging to return results
     * <p> Use {@link DiscountService#getAllDiscountsOfTheCurrentWeek(Pageable, Optional)} instead.
     */
    @Deprecated(since = "0.0.1-SNAPSHOT")
    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek();

    Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek(Pageable pageable, Optional<String> productName);

    DiscountDTOResponse saveDiscount(DiscountDTORequest discountDTORequest);
}
