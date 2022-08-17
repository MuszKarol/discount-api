package pl.MuszKarol.DiscountAPI.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "discount")
public class Discount extends BasicEntity implements Serializable {

    private Date discountStartDate;
    private Date discountEndDate;
    private String url;
    private Long basePrice;
    private Long newPrice;
    private Integer likes;
    private Integer dislikes;

    @ManyToOne
    private Product product;
}
