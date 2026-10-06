package pl.musz.karol.discountapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.title:DiscountAPI}") String title,
                                 @Value("${application.description:}") String description,
                                 @Value("${application.version:}") String version) {

        return new OpenAPI().info(getInfo(title, description, version));
    }

    private Info getInfo(String title, String description, String version) {
        if (description.isBlank() || version.isBlank()) {
            return new Info().title(title);
        } else {
            return new Info().title(title)
                    .description(description)
                    .version(version);
        }
    }
}
