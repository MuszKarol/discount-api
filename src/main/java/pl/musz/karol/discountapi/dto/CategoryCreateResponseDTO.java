package pl.musz.karol.discountapi.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryCreateResponseDTO {
    private String categoryName;
    private Integer categoryLevel;
    private List<UUID> productIDs;
    private List<UUID> fullPathToRoot;
    private List<SubcategoryDTO> subcategories;
}
