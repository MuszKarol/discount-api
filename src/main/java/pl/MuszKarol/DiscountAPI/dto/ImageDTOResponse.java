package pl.MuszKarol.DiscountAPI.dto;


import lombok.Builder;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
public class ImageDTOResponse {
    public UUID discountId;
    public String imageName;
    public boolean saved;
    public Timestamp timestamp;
}
