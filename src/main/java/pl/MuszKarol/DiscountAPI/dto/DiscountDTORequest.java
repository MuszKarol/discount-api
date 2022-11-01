package pl.MuszKarol.DiscountAPI.dto;

import lombok.*;

import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTORequest {
    private String url;
    private String productName;
    private String description;
    private Date discountStartDate;
    private Date discountEndDate;
    private Long basePrice;
    private Long newPrice;
}
