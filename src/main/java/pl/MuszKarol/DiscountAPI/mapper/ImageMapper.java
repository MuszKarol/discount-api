package pl.MuszKarol.DiscountAPI.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.MuszKarol.DiscountAPI.dto.ImageIdentificationDetailsDTO;
import pl.MuszKarol.DiscountAPI.model.Image;

@Mapper
public interface ImageMapper {

    @Mappings({
            @Mapping(source = "image.imageName", target = "imageName"),
            @Mapping(source = "image.extension", target = "imageExtension")
    })
    ImageIdentificationDetailsDTO imageToImageDTO(Image image);
}
