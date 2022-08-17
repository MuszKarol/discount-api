package pl.MuszKarol.DiscountAPI.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.service.implementation.DiscountServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/discounts")
public class DiscountController {

    private final DiscountServiceImpl discountService;

    @GetMapping(value = "/month")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneMonth() {
        return ResponseEntity.ok().body(
                discountService.takeDiscountsByDate(
                        Date.valueOf(LocalDate.now().minusMonths(1)),
                        Date.valueOf(LocalDate.now())
                ));
    }

    @GetMapping(value = "/week")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneWeek() {
        return ResponseEntity.ok().body(
                discountService.takeDiscountsByDate(
                        Date.valueOf(LocalDate.now().minusWeeks(1)),
                        Date.valueOf(LocalDate.now())
                ));
    }


    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DiscountDTOResponse> saveDiscount(
            @RequestPart("discount") DiscountDTORequest discountDTORequest,
            @RequestPart("image") MultipartFile image) {
        DiscountDTOResponse discountDTOResponse = discountService.saveDiscount(image, discountDTORequest);

        return ResponseEntity.ok().body(discountDTOResponse);
    }
}
