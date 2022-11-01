package pl.MuszKarol.DiscountAPI.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;

import java.util.List;

public interface DiscountService {
    @Deprecated
    List<DiscountDTOResponse> getAllDiscounts();

    Page<DiscountDTOResponse> getAllDiscounts(Pageable pageable);

    @Deprecated
    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth();

    Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth(Pageable pageable);

    @Deprecated
    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek();

    Page<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek(Pageable pageable);

    DiscountDTOResponse saveDiscount(DiscountDTORequest discountDTORequest);
}
