package pl.MuszKarol.DiscountAPI.dto;

import java.sql.Date;

public class DiscountDTOResponse {
    public String discountId;
    public String url;
    public String productName;
    public String description;
    public Date discountStartDate;
    public Date discountEndDate;
    public Long basePrice;
    public Long newPrice;
    public Integer likes;
    public Integer dislikes;
}
