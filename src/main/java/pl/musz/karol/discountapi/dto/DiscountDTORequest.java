package pl.musz.karol.discountapi.dto;

import lombok.*;
import pl.musz.karol.discountapi.util.validator.pattern.DiscountValidationPatterns;

import javax.validation.constraints.Pattern;
import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTORequest {

    @Pattern(regexp = DiscountValidationPatterns.URL_PATTERN)
    private String url;

    @Pattern(regexp = DiscountValidationPatterns.PRODUCT_NAME_PATTERN)
    private String productName;

    @Pattern(regexp = DiscountValidationPatterns.PRODUCT_DESCRIPTION_PATTERN)
    private String description;

    private Date discountStartDate;
    private Date discountEndDate;
    private Long basePrice;
    private Long newPrice;
}
