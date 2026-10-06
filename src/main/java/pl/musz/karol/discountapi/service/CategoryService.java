package pl.musz.karol.discountapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.musz.karol.discountapi.dto.CategoryCreateRequestDTO;
import pl.musz.karol.discountapi.dto.CategoryCreateResponseDTO;
import pl.musz.karol.discountapi.dto.CategoryResponseDTO;
import pl.musz.karol.discountapi.exception.CategoryNotFoundException;

import java.util.UUID;

public interface CategoryService {
    CategoryCreateResponseDTO createNewCategory(CategoryCreateRequestDTO categoryCreateRequestDTO) throws CategoryNotFoundException;

    Page<CategoryResponseDTO> getCategoriesByMatchingPattern(String pattern, Pageable pageable);

    CategoryResponseDTO getCategoryById(UUID categoryId) throws CategoryNotFoundException;

    Page<CategoryResponseDTO> getCategoryByProductId(UUID productId, Pageable pageable) throws IllegalArgumentException;
}
