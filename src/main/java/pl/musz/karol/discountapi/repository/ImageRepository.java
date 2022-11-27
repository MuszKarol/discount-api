package pl.musz.karol.discountapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.musz.karol.discountapi.model.Image;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}