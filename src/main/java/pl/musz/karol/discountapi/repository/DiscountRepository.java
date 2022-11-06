package pl.musz.karol.discountapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.musz.karol.discountapi.model.Discount;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Page<Discount> findAllBy(Pageable pageable);

    @Query(value = "SELECT d FROM Discount d WHERE d.discountStartDate >= :discountStartDate AND d.discountEndDate <= :discountEndDate")
    List<Discount> getDiscountByGivenDates(Date discountStartDate, Date discountEndDate);

    @Query(value = "SELECT d FROM Discount d WHERE d.discountStartDate >= :discountStartDate AND d.discountEndDate <= :discountEndDate")
    Page<Discount> getDiscountByGivenDates(Date discountStartDate, Date discountEndDate, Pageable pageable);
}
