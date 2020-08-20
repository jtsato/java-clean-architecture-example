package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


/**
 * @author Jorge Takeshi Sato
 */

@Profile({"uat","prod"})
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity httpSecurity)
        throws Exception {
        
        httpSecurity.authorizeRequests().anyRequest().authenticated().and().oauth2Login().and().oauth2ResourceServer().jwt();
        httpSecurity.requiresChannel().requestMatchers(request -> request.getHeader("X-Forwarded-Proto") != null).requiresSecure();
        httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        httpSecurity.headers().contentSecurityPolicy("script-src 'self'; report-to /csp-report-endpoint/");
    }
}
