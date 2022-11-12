package pl.musz.karol.discountapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Image extends BasicEntity {

    @NotBlank
    private String imageName;

    @NotBlank
    private String extension;
}
