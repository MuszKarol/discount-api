package pl.musz.karol.discountapi.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SubcategoryDTO {
    private UUID id;
    private String subcategoryName;
}
