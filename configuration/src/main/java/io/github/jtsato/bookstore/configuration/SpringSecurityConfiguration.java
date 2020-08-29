package io.github.jtsato.bookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

/**
 * @author Jorge Takeshi Sato
 */

@Profile({"uat", "prod"})
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity httpSecurity)
        throws Exception {
        httpSecurity.authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer().jwt();
        Okta.configureResourceServer401ResponseBody(httpSecurity);
    }

    @Override
    public void configure(final WebSecurity web)
        throws Exception {
        web.ignoring().antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }
}
