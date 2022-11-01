package pl.MuszKarol.DiscountAPI.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTOResponse {
    private String discountId;
    private String url;
    private String productName;
    private String description;
    private Date discountStartDate;
    private Date discountEndDate;
    private Long basePrice;
    private Long newPrice;
    private Integer likes;
    private Integer dislikes;
    private List<ImageIdentificationDetailsDTO> images;
}
