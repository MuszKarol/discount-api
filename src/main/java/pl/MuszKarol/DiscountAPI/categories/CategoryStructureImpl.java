package pl.MuszKarol.DiscountAPI.categories;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import pl.MuszKarol.DiscountAPI.model.Category;
import pl.MuszKarol.DiscountAPI.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApplicationScope
@Component
public class CategoryStructureImpl implements CategoryStructure {

    private final CategoryRepository categoryRepository;
    private Category root;

    public CategoryStructureImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        setRootCategory();
        createNewCategory();
    }

    @Override
    public List<Category> getFullPathToRoot(Category category) {
        List<Category> categories = new LinkedList<>(); //TODO performance?
        Category actualCategory = category;

        while (actualCategory != null) {    //TODO
            categories.add(actualCategory);
            actualCategory = actualCategory.getParent();
        }

        Collections.reverse(categories);

        return categories;
    }

    @Override
    public List<Category> getSubcategories(Category parentCategory) {
        return parentCategory.getSubcategories();
    }

    @Override
    public Category getParentCategory(Category childCategory) {
        if (childCategory.equals(root)) {
            throw new IllegalArgumentException("Parent of root not exists!");   //TODO rename error!
        }

        return childCategory.getParent();
    }

    @Override
    public Category addNewCategoryToParent(Category parentCategory, Category childCategory) {
        List<Category> subcategories = parentCategory.getSubcategories();

        if (childCategory.getSubcategories() == null) {
            childCategory.setSubcategories(new ArrayList<>());
            childCategory.setParent(parentCategory);
        }

        subcategories.add(categoryRepository.save(childCategory));

        return categoryRepository.save(parentCategory);
    }

    @Override
    public Category getRootCategory() {
        return root;
    }

    private void setRootCategory() {
        categoryRepository.findCategoryByParentIsNull()
                .ifPresent((category) -> root = category);
    }

    private void createNewCategory() {
        Category rootCategory = Category.builder()
                .name("ROOT")
                .subcategories(new ArrayList<>())
                .build();

        root = categoryRepository.save(rootCategory);
    }
}
