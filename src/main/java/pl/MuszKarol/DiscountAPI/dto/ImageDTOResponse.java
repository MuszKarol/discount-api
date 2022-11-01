package pl.MuszKarol.DiscountAPI.dto;


import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTOResponse {
    private UUID discountId;
    private String imageName;
    private boolean saved;
    private Timestamp timestamp;
}
