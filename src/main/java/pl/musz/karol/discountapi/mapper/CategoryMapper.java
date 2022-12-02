package pl.musz.karol.discountapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.musz.karol.discountapi.dto.CategoryCreateResponseDTO;
import pl.musz.karol.discountapi.dto.CategoryResponseDTO;
import pl.musz.karol.discountapi.dto.SubcategoryDTO;
import pl.musz.karol.discountapi.model.Category;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CategoryMapper {

    @Mapping(source = "category.id", target = "id")
    @Mapping(source = "category.name", target = "subcategoryName")
    SubcategoryDTO subcategoryToSubcategoryDTO(Category category);

    @Mapping(source = "categoryName", target = "categoryName")
    @Mapping(source = "listOfProductIds", target = "productIDs")
    @Mapping(source = "path", target = "fullPathToRoot")
    @Mapping(source = "subcategoryDTOList", target = "subcategories")
    CategoryResponseDTO categoryToCategoryResponseDTO(String categoryName,
                                                      List<UUID> listOfProductIds,
                                                      List<UUID> path,
                                                      List<SubcategoryDTO> subcategoryDTOList);

    @Mapping(source = "categoryName", target = "categoryName")
    @Mapping(source = "level", target = "categoryLevel")
    @Mapping(source = "listOfProductIds", target = "productIDs")
    @Mapping(source = "path", target = "fullPathToRoot")
    @Mapping(source = "subcategoryDTOList", target = "subcategories")
    CategoryCreateResponseDTO categoryToCategoryCreateResponseDTO(String categoryName,
                                                                  Integer level,
                                                                  List<UUID> listOfProductIds,
                                                                  List<UUID> path,
                                                                  List<SubcategoryDTO> subcategoryDTOList);
}
