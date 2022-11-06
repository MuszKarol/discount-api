package pl.musz.karol.discountapi.model;

import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Image extends BasicEntity {
    private String imageName;
    private String extension;
}
