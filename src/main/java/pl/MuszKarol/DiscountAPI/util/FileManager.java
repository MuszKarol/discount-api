package pl.MuszKarol.DiscountAPI.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {

    private final Path path;

    FileManager() {
        path = locateFolderToStoreImages();
    }

    private static Path locateFolderToStoreImages() {
        return Paths.get("src/main/resources/images/")
                .toAbsolutePath();
    }

    public File getNewFile(String fileName, String fileExtension) {
        String pathname = getFilePath(fileName, fileExtension);
        return new File(pathname);
    }

    public InputStreamResource getImageAsInputStreamResource(String filename, String fileExtension) throws FileNotFoundException {
        String pathname = getFilePath(filename, fileExtension);
        File file = new File(pathname);

        return new InputStreamResource(new FileInputStream(file));
    }

    private String getFilePath(String fileName, String fileExtension) {
        return path.toString() + "\\" + fileName + "." + fileExtension;
    }
}
