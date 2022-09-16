package pl.MuszKarol.DiscountAPI.service;

import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;

import java.util.List;

public interface DiscountService {
    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentMonth();

    List<DiscountDTOResponse> getAllDiscountsOfTheCurrentWeek();

    DiscountDTOResponse saveDiscount(DiscountDTORequest discountDTORequest);
}
