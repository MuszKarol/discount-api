package pl.MuszKarol.DiscountAPI.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageIdentificationDetailsDTO {
    private String imageName;
    private String imageExtension;
}
