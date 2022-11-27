package pl.musz.karol.discountapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.musz.karol.discountapi.dto.DiscountDTOResponse;
import pl.musz.karol.discountapi.dto.ImageIdentificationDetailsDTO;
import pl.musz.karol.discountapi.model.Discount;

import java.util.List;

@Mapper
public interface DiscountMapper {

    @Mapping(source = "discount.id", target = "discountId")
    @Mapping(source = "discount.url", target = "url")
    @Mapping(source = "discount.discountStartDate", target = "discountStartDate")
    @Mapping(source = "discount.discountEndDate", target = "discountEndDate")
    @Mapping(source = "discount.basePrice", target = "basePrice")
    @Mapping(source = "discount.newPrice", target = "newPrice")
    @Mapping(source = "discount.likes", target = "likes")
    @Mapping(source = "discount.dislikes", target = "dislikes")
    @Mapping(source = "discount.product.name", target = "productName")
    @Mapping(source = "discount.product.description", target = "description")
    @Mapping(source = "listOfImageDTO", target = "images")
    DiscountDTOResponse discountToDiscountDTOResponse(Discount discount, List<ImageIdentificationDetailsDTO> listOfImageDTO);
}
