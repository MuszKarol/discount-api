package pl.musz.karol.discountapi.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryResponseDTO {
    private UUID id;
    private String categoryName;
    private List<UUID> productIDs;
    private List<UUID> fullPathToRoot;
    private List<SubcategoryDTO> subcategories;
}
