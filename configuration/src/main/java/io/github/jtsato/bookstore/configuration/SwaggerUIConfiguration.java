package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.jtsato.bookstore.BookstoreApplication;

/**
 * @author Jorge Takeshi Sato
 */

@Profile("!prod")
@Configuration
@ComponentScan(basePackageClasses = BookstoreApplication.class)
@Import(SwaggerConfiguration.class)
public class SwaggerUIConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
