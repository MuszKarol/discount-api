package pl.musz.karol.discountapi.util.formatter;

public class ImageExtensionFormatter {
    public static String formatExtension(String extension) {
        return extension.replace("image/", "").toLowerCase();
    }
}
