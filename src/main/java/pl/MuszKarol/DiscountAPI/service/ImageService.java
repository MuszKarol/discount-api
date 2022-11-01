package pl.MuszKarol.DiscountAPI.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.ImageDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.exception.InvalidImageExtensionException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageService {
    Resource getImageAsResource(String filename, String imageExtension)
            throws ImageNotFoundException, InvalidImageExtensionException;

    List<ImageDTOResponse> saveImages(List<MultipartFile> multipartFileList, UUID discountUuid)
            throws IOException, InvalidImageExtensionException;
}
