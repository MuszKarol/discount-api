package pl.MuszKarol.DiscountAPI.categories;

import pl.MuszKarol.DiscountAPI.model.Category;

import java.util.List;

public interface CategoryStructure {
    List<Category> getFullPathToRoot(Category category);

    List<Category> getSubcategories(Category parentCategory);

    Category getParentCategory(Category childCategory);

    Category addNewCategoryToParent(Category parentCategory, Category childCategory);

    Category getRootCategory();
}
