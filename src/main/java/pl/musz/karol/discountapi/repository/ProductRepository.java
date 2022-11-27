package pl.musz.karol.discountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.musz.karol.discountapi.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> getProductByName(String name);
}