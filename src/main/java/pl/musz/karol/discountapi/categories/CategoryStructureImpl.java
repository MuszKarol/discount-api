package pl.musz.karol.discountapi.categories;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import pl.musz.karol.discountapi.model.Category;
import pl.musz.karol.discountapi.repository.CategoryRepository;

import java.util.*;

@ApplicationScope
@Component
public class CategoryStructureImpl implements CategoryStructure {

    private final CategoryRepository categoryRepository;
    private Category root;

    public CategoryStructureImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getFullPathToRoot(Category category) {
        List<Category> categories = new LinkedList<>();

        while (category != null) {
            categories.add(category);
            category = category.getParent();
        }

        Collections.reverse(categories);

        return categories;
    }

    @Override
    public List<Category> getSubcategories(Category category) {
        return category.getSubcategories();
    }

    @Override
    public Category getParentCategory(Category category) {
        if (category.equals(root)) {
            throw new IllegalArgumentException("Parent of root not exists!");
        }

        return category.getParent();
    }

    @Override
    public Category addNewCategory(Category parentCategory, Category category) {
        List<Category> rootSubcategories = parentCategory.getSubcategories();

        category.setParent(parentCategory);
        category.setLevel(parentCategory.getLevel() + 1);
        rootSubcategories.add(categoryRepository.save(category));
        categoryRepository.save(parentCategory);

        return category;
    }

    @Override
    public Category getRootCategory() {
        return root;
    }

    @Override
    public List<Category> findCategoriesByName(String categoryName) {
        return categoryRepository.findCategoryByNameContaining(categoryName);
    }

    @Override
    public void setRootCategory() {
        Optional<Category> rootOptional = categoryRepository.findCategoryByParentIsNull();

        if (rootOptional.isPresent()) {
            root = rootOptional.get();
        } else {
            createRootCategory();
        }
    }

    private void createRootCategory() {
        Category rootCategory = Category.builder()
                .name("ROOT")
                .level(0)
                .subcategories(new ArrayList<>())
                .products(new ArrayList<>())
                .build();

        root = categoryRepository.save(rootCategory);
    }
}
