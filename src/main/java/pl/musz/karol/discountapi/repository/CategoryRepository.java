package pl.musz.karol.discountapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.musz.karol.discountapi.model.Category;
import pl.musz.karol.discountapi.model.Product;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findCategoryByParentIsNull();

    @Query(value = "SELECT MAX(c.level) FROM Category c")
    Integer findLatestLevel();

    Optional<Category> findFirstByLevel(@NotBlank Integer level);

    @Query(value = "SELECT c.* FROM Category c WHERE (c.name REGEXP :regex)", nativeQuery = true)
    List<Category> searchCategoryByNameRegex(@Param("regex") String regexName);

    @Query(value = "SELECT c.* FROM Category c WHERE (c.name REGEXP :regex)", nativeQuery = true)
    Page<Category> getPageableCategoriesByNameRegex(@Param("regex") String regexName, Pageable pageable);

    Page<Category> getCategoriesByProductsContains(Product product, Pageable pageable);
}