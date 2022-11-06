package pl.musz.karol.discountapi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.musz.karol.discountapi.dto.ImageDTOResponse;
import pl.musz.karol.discountapi.exception.ImageNotFoundException;
import pl.musz.karol.discountapi.exception.InvalidImageExtensionException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageService {

    Resource getImageAsResource(String filename, String imageExtension)
            throws ImageNotFoundException, InvalidImageExtensionException;

    List<ImageDTOResponse> saveAllImages(List<MultipartFile> multipartFileList, UUID discountId)
            throws IOException, InvalidImageExtensionException;
}
