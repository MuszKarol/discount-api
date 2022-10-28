package pl.MuszKarol.DiscountAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.MuszKarol.DiscountAPI.model.Image;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}