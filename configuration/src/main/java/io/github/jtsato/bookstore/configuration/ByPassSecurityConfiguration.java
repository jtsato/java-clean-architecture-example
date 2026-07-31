package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Jorge Takeshi Sato
 */

@Profile({"test", "dev"})
@Configuration
public class ByPassSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) {
        httpSecurity
            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
            .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
        return httpSecurity.build();
    }
}
