package pl.musz.karol.discountapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.musz.karol.discountapi.dto.ImageIdentificationDetailsDTO;
import pl.musz.karol.discountapi.model.Image;

@Mapper
public interface ImageMapper {

    @Mapping(source = "image.imageName", target = "imageName")
    @Mapping(source = "image.extension", target = "imageExtension")
    ImageIdentificationDetailsDTO imageToImageDTO(Image image);
}
