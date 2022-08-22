package pl.MuszKarol.DiscountAPI.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.service.DiscountService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

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

    @GetMapping("/images")
    public ResponseEntity<Resource> downloadImage(@RequestParam String imageName, @RequestParam String extension) {

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(discountService.getImageAsResource(imageName, extension));
        } catch (ImageNotFoundException e) {
            return ResponseEntity.badRequest()
                    .build();
        }
    }

    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveDiscount(
            @RequestPart("discount") @Valid DiscountDTORequest discountDTORequest,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image) {

        try {
            return ResponseEntity.ok().body(discountService.saveDiscount(image, discountDTORequest));
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
