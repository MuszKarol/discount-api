package pl.musz.karol.discountapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "product")
public class Product extends BasicEntity {

    @NotBlank
    private String name;

    private String description;

    @ManyToOne
    private Category category;
}
