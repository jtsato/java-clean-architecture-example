package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author Jorge Takeshi Sato
 */

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Bookstore Service")
                                            .version("v1")
                                            .description("Java Clean Architecture Example © 2020")
                                            .termsOfService("https://jtsato.github.io/terms-conditions.html")
                                            .license(new License().name("Apache License 2.0")
                                                                  .url("https://raw.githubusercontent.com/jtsato/java-clean-architecture-example/master/LICENSE")));
    }
}
