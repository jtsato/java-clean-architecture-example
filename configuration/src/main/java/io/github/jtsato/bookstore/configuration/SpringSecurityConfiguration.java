package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;

import com.okta.spring.boot.oauth.Okta;

/**
 * @author Jorge Takeshi Sato
 */

@Profile({"uat", "prod"})
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        Okta.configureResourceServer401ResponseBody(httpSecurity);
        return httpSecurity.build();
    }
}
