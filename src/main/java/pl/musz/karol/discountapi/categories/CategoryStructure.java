package pl.musz.karol.discountapi.categories;

import pl.musz.karol.discountapi.model.Category;

import java.util.List;

public interface CategoryStructure {
    List<Category> getFullPathToRoot(Category category);

    List<Category> getSubcategories(Category parentCategory);

    Category getParentCategory(Category category);

    Category addNewCategory(Category parentCategory, Category category);

    void setRootCategory();

    Category getRootCategory();

    List<Category> findCategoriesByName(String categoryName);
}
