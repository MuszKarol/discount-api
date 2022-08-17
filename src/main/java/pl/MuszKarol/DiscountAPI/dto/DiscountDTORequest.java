package pl.MuszKarol.DiscountAPI.dto;

import java.sql.Date;

public class DiscountDTORequest {
    public String url;
    public String imageExtension;
    public String productName;
    public String description;
    public Date discountStartDate;
    public Date discountEndDate;
    public Long basePrice;
    public Long newPrice;
}
