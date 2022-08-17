package pl.MuszKarol.DiscountAPI.service;

import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;

import java.sql.Date;
import java.util.List;

public interface DiscountService {
    List<DiscountDTOResponse> takeDiscountsByDate(Date startDate, Date endDate);
    DiscountDTOResponse saveDiscount(MultipartFile multipartFile, DiscountDTORequest discountDTORequest);
}
