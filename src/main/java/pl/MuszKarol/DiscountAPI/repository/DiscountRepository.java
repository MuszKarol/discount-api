package pl.MuszKarol.DiscountAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.MuszKarol.DiscountAPI.model.Discount;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    List<Discount> getDiscountsByDiscountEndDateLessThanAndDiscountStartDateGreaterThan(Date discountEndDate, Date discountStartDate);
}
