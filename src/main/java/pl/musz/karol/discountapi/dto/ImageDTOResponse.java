package pl.musz.karol.discountapi.dto;


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
    private String imageExtension;
    private boolean saved;
    private Timestamp timestamp;
}
