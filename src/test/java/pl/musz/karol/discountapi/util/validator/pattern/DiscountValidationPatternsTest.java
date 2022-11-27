package pl.musz.karol.discountapi.util.validator.pattern;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DiscountValidationPatternsTest {

    @Test
    public void urlPatternDoesNotMatchTest() {
        String invalidUrl = "https://docs_spring-io/";

        boolean result = getMatchResult(invalidUrl,
                DiscountValidationPatterns.URL_PATTERN);

        assertFalse(result);
    }

    @Test
    public void urlPatternDoesMatchTest() {
        String url = "https://docs.spring.io/";

        boolean result = getMatchResult(url,
                DiscountValidationPatterns.URL_PATTERN);

        assertTrue(result);
    }

    @Test
    public void productNamePatternDoesNotMatchTest() {
        String invalidProductName = "Example Product 8.";

        boolean result = getMatchResult(invalidProductName,
                DiscountValidationPatterns.PRODUCT_NAME_PATTERN);

        assertFalse(result);
    }

    @Test
    public void productNamePatternDoesMatchTest() {
        String productName = "Example Product 8";

        boolean result = getMatchResult(productName,
                DiscountValidationPatterns.PRODUCT_NAME_PATTERN);

        assertTrue(result);
    }

    @Test
    public void productDescriptionPatternDoesNotMatchTest() {
        String invalidProductDescription = "Test description 1$.";

        boolean result = getMatchResult(invalidProductDescription,
                DiscountValidationPatterns.PRODUCT_DESCRIPTION_PATTERN);

        assertFalse(result);
    }

    @Test
    public void productDescriptionPatternDoesMatchTest() {
        String productDescription = "Test description 1.";

        boolean result = getMatchResult(productDescription,
                DiscountValidationPatterns.PRODUCT_DESCRIPTION_PATTERN);

        assertTrue(result);
    }

    private boolean getMatchResult(String example, String validationPattern) {
        Pattern pattern = Pattern.compile(validationPattern);
        Matcher matcher = pattern.matcher(example);
        return matcher.matches();
    }
}