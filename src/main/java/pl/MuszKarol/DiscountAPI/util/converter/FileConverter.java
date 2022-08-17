package pl.MuszKarol.DiscountAPI.util.converter;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileConverter {
    public static boolean convertMultipartFileToFile(MultipartFile multipartFile, File file) {
        try {
            multipartFile.transferTo(file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
