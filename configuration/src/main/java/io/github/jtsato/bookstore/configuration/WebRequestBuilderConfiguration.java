package io.github.jtsato.bookstore.configuration;


import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.github.jtsato.bookstore.entrypoint.rest.common.WebRequest;

/**
 * @author Jorge Takeshi Sato
 */

@Configuration
public class WebRequestBuilderConfiguration {

    @Bean
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public WebRequest getWebRequest() {
        return buildWebRequest(getRequest());
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private WebRequest buildWebRequest(final HttpServletRequest request) {
        return new WebRequest(request.getRequestURI());
    }
}
