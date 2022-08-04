package pl.MuszKarol.DiscountAPI.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "product")
public class Product extends BasicEntity {

    private String name;
    private String description;
    private String imageName;

    @ManyToOne
    private Category category;
}
