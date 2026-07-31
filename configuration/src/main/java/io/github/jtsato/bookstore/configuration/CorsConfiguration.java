package io.github.jtsato.bookstore.configuration;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class CorsConfiguration implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
        throws IOException, ServletException {

        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, HEAD, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        response.setHeader("Access-Control-Expose-Headers", "Location");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        // Not implemented.
    }

    @Override
    public void destroy() {
        // Not implemented.
    }
}
