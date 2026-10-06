package pl.musz.karol.discountapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Discount extends BasicEntity {

    private Date discountStartDate;
    private Date discountEndDate;
    private Long basePrice;
    private String url;
    private Long newPrice;
    private Integer likes;
    private Integer dislikes;

    @OneToMany
    private List<Image> images = new LinkedList<>();

    @ManyToOne
    private Product product;
}
