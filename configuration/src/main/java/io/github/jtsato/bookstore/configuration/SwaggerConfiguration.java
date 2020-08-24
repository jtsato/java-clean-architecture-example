package io.github.jtsato.bookstore.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author Jorge Takeshi Sato
 */

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        final License license = new License().name("Apache License 2.0")
                                             .url("https://raw.githubusercontent.com/jtsato/java-clean-architecture-example/master/LICENSE");

        final String title = String.format("%s API", StringUtils.capitalize("Bookstore Service"));

        final Info info = new Info().title(title)
                                    .version("v1")
                                    .description("Java Clean Architecture Example © 2020")
                                    .termsOfService("https://jtsato.github.io/terms-conditions.html")
                                    .license(license);

        final String securitySchemeName = "Token";

        final SecurityScheme securityScheme = new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
        final Components components = new Components().addSecuritySchemes(securitySchemeName, securityScheme);
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName)).components(components).info(info);
    }
}
