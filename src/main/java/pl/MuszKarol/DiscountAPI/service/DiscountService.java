package pl.MuszKarol.DiscountAPI.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.exception.InvalidExtensionException;

import java.sql.Date;
import java.util.List;

public interface DiscountService {
    List<DiscountDTOResponse> takeDiscountsByDate(Date startDate, Date endDate);

    DiscountDTOResponse saveDiscount(MultipartFile multipartFile, DiscountDTORequest discountDTORequest) throws FileUploadException, InvalidExtensionException;

    Resource getImageAsResource(String filename, String imageExtension) throws ImageNotFoundException, InvalidExtensionException;
}
