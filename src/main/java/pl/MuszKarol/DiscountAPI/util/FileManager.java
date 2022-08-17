package pl.MuszKarol.DiscountAPI.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class FileManager {

    private final String path;

    FileManager() {
        path = getAbsolutePath() + "\\src\\main\\resources\\images\\";
    }

    public File getNewFile(String fileName, String fileExtension) {
        return new File(path + fileName + fileExtension);
    }

    private static Path getAbsolutePath() {
        return FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath();
    }
}
