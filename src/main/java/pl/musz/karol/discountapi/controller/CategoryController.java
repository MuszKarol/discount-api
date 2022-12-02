package pl.musz.karol.discountapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.musz.karol.discountapi.dto.CategoryCreateRequestDTO;
import pl.musz.karol.discountapi.dto.CategoryCreateResponseDTO;
import pl.musz.karol.discountapi.dto.CategoryResponseDTO;
import pl.musz.karol.discountapi.exception.CategoryNotFoundException;
import pl.musz.karol.discountapi.service.CategoryService;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@RestController
public class CategoryController {
    
    private final CategoryService categoryService;

    @GetMapping(value = "/{category-id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("category-id") UUID id) {
        try {
            return ResponseEntity.ok()
                    .body(categoryService.getCategoryById(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/products/{product-id}")
    public ResponseEntity<Page<CategoryResponseDTO>> getCategoryByProductId(@PathVariable("product-id") UUID id,
                                                                      Pageable pageable) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoryByProductId(id, pageable));
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam("categoryName") String categoryPattern,
            Pageable pageable) {

        return ResponseEntity.ok()
                .body(categoryService.getCategoriesByMatchingPattern(categoryPattern, pageable));
    }

    @PostMapping(value = "/new")
    public ResponseEntity<CategoryCreateResponseDTO> addNewCategory(
            @RequestBody CategoryCreateRequestDTO categoryCreateRequestDTO) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryService.createNewCategory(categoryCreateRequestDTO));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
