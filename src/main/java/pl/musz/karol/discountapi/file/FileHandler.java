package pl.musz.karol.discountapi.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileHandler {

    private final Path path;
    private final String pathFromContentRoot;

    FileHandler(@Value("${image.folder.root.path:src/main/resources/images}") String pathFromContentRoot) {
        this.pathFromContentRoot = pathFromContentRoot;
        path = locateFolderToStoreImages();
    }

    public File getNewFile(String fileName, String fileExtension) {
        String pathname = getFilePath(fileName, fileExtension);
        return new File(pathname);
    }

    public InputStreamResource getFileAsInputStreamResource(String filename, String fileExtension)
            throws FileNotFoundException {

        String pathname = getFilePath(filename, fileExtension);
        File file = new File(pathname);

        return new InputStreamResource(new FileInputStream(file));
    }

    private Path locateFolderToStoreImages() {
        return Paths.get(pathFromContentRoot)
                .toAbsolutePath();
    }

    private String getFilePath(String fileName, String fileExtension) {
        return new StringBuilder()
                .append(path)
                .append(File.separator)
                .append(fileName)
                .append(".")
                .append(fileExtension)
                .toString();
    }
}
