package pl.musz.karol.discountapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.musz.karol.discountapi.dto.CategoryCreateRequestDTO;
import pl.musz.karol.discountapi.dto.CategoryCreateResponseDTO;
import pl.musz.karol.discountapi.dto.CategoryResponseDTO;
import pl.musz.karol.discountapi.exception.CategoryNotFoundException;
import pl.musz.karol.discountapi.service.CategoryService;

import java.util.UUID;

@AllArgsConstructor
@RequestMapping(value = "/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get category by ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Category was found",
            content = {
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CategoryResponseDTO.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Category with specified ID not found"
        )
    })
    @GetMapping(value = "/{category-id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("category-id") UUID id) {
        try {
            return ResponseEntity.ok()
                    .body(categoryService.getCategoryById(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all categories by assigned product ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found categories for the assigned product",
            content = {
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))
                )
            }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Cannot find a product with the specified id"
        ),
    })
    @GetMapping(value = "/products/{product-id}")
    public ResponseEntity<Page<CategoryResponseDTO>> getCategoryByProductId(
            @PathVariable("product-id") UUID id,
            @ParameterObject Pageable pageable) {

        try {
            return ResponseEntity.ok().body(categoryService.getCategoryByProductId(id, pageable));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all available categories by product name pattern")
    @ApiResponse(
        responseCode = "200",
        description = "Matching categories found",
        content = {
            @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))
            )
        }
    )
    @GetMapping(value = "/")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam("categoryName") String categoryPattern,
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok()
                .body(categoryService.getCategoriesByMatchingPattern(categoryPattern, pageable));
    }

    @Operation(summary = "Post a new category")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "A new category has been created",
            content = {
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CategoryCreateResponseDTO.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Incorrect request content"
        )
    })
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
