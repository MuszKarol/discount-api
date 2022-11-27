package pl.musz.karol.discountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.musz.karol.discountapi.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findCategoryByParentIsNull();
}