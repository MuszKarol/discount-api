package pl.musz.karol.discountapi.util.validator.pattern;

public class DiscountValidationPatterns {
    public static final String URL_PATTERN = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$";
    public static final String PRODUCT_NAME_PATTERN = "^[0-9A-Za-zÀ-ȕ\\s_-]+$";
    public static final String PRODUCT_DESCRIPTION_PATTERN = "^[0-9A-Za-zÀ-ȕ\\s,.~()?!]+$";
}