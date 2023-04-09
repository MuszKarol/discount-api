package pl.musz.karol.discountapi.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.musz.karol.discountapi.categories.CategoryStructure;
import pl.musz.karol.discountapi.dto.CategoryCreateRequestDTO;
import pl.musz.karol.discountapi.dto.CategoryCreateResponseDTO;
import pl.musz.karol.discountapi.dto.CategoryResponseDTO;
import pl.musz.karol.discountapi.dto.SubcategoryDTO;
import pl.musz.karol.discountapi.exception.CategoryNotFoundException;
import pl.musz.karol.discountapi.mapper.CategoryMapper;
import pl.musz.karol.discountapi.model.Category;
import pl.musz.karol.discountapi.model.Product;
import pl.musz.karol.discountapi.repository.CategoryRepository;
import pl.musz.karol.discountapi.repository.ProductRepository;
import pl.musz.karol.discountapi.service.CategoryService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryStructure categoryStructure;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    @PostConstruct
    public void postConstructRootCategoryCreation() {
        categoryStructure.setRootCategory();
    }

    @Override
    public CategoryCreateResponseDTO createNewCategory(CategoryCreateRequestDTO categoryDTO)
            throws CategoryNotFoundException {

        Category parentCategory = retrieveParentCategory(categoryDTO);
        Category category = buildNewCategory(categoryDTO);

        return getCategoryCreateResponseDTO(categoryStructure.addNewCategory(parentCategory, category));
    }

    @Override
    public Page<CategoryResponseDTO> getCategoriesByMatchingPattern(String pattern, Pageable pageable) {
        return categoryRepository.findCategoryByNameContaining(pattern, pageable)
                .map(this::getCategoryResponseDTO);
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Cannot find a category with the specified id");
        }

        return getCategoryResponseDTO(categoryOptional.get());
    }

    @Override
    public Page<CategoryResponseDTO> getCategoryByProductId(UUID productId, Pageable pageable)
            throws IllegalArgumentException {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Cannot find a product with the specified id");
        }

        return categoryRepository.getCategoriesByProductsContains(productOptional.get(), pageable)
                .map(this::getCategoryResponseDTO);
    }

    private Category buildNewCategory(CategoryCreateRequestDTO categoryDTO) {
        return Category.builder().name(categoryDTO.getCategoryName()).build();
    }

    private List<UUID> getProductIds(Category category) {
        if (category.getProducts() != null) {
            return category.getProducts()
                    .stream()
                    .map(Product::getId)
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    private CategoryResponseDTO getCategoryResponseDTO(Category category) {
        List<UUID> productIds = getProductIds(category);
        List<UUID> path = getPath(category);
        List<SubcategoryDTO> subcategoryDTOList = getSubcategoryDTOList(category);

        return categoryMapper.categoryToCategoryResponseDTO(category.getId(), category.getName(), productIds,
                path, subcategoryDTOList);
    }

    private CategoryCreateResponseDTO getCategoryCreateResponseDTO(Category category) {
        List<UUID> productIds = getProductIds(category);
        List<UUID> path = getPath(category);
        List<SubcategoryDTO> subcategoryDTOList = getSubcategoryDTOList(category);

        return categoryMapper.categoryToCategoryCreateResponseDTO(category.getId(), category.getName(),
                category.getLevel(), productIds, path, subcategoryDTOList);
    }

    private Category retrieveParentCategory(CategoryCreateRequestDTO categoryDTO) throws CategoryNotFoundException {
        Optional<Category> parentCategoryOptional = categoryRepository.findById(categoryDTO.getParentCategoryId());

        if (parentCategoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Unable to find parent category: " + categoryDTO.getCategoryName());
        }

        return parentCategoryOptional.get();
    }

    private List<SubcategoryDTO> getSubcategoryDTOList(Category category) {
        if (category.getSubcategories() != null) {
            return category.getSubcategories()
                    .stream()
                    .map(categoryMapper::subcategoryToSubcategoryDTO)
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    private List<UUID> getPath(Category category) {
        return categoryStructure.getFullPathToRoot(category)
                .stream()
                .map(Category::getId)
                .toList();
    }
}
