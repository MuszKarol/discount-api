package pl.MuszKarol.DiscountAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.MuszKarol.DiscountAPI.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findCategoryByParentIsNull();
}