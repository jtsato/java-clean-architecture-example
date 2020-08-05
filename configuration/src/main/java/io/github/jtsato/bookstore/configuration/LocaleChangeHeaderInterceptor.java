package io.github.jtsato.bookstore.configuration;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author Jorge Takeshi Sato  
 */

public class LocaleChangeHeaderInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
        throws ServletException {

        final String newLocale = request.getHeader(getParamName());
        if (newLocale != null) {
            final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found!");
            }
            try {
                localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
            } catch (final IllegalArgumentException ex) {
                logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
            }
        }
        return true;
    }
}
