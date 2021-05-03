package io.github.jtsato.bookstore.configuration;


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
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        final String newLocale = request.getHeader(getParamName());

        if (newLocale != null) {
            final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            setNewLocale(request, response, newLocale, localeResolver);
        }

        return true;
    }

    private void setNewLocale(final HttpServletRequest request, final HttpServletResponse response, final String locale, final LocaleResolver localeResolver) {
        try {
            localeResolver.setLocale(request, response, parseLocaleValue(locale));
        } catch (final IllegalArgumentException ex) {
            if (!isIgnoreInvalidLocale()) {
                throw ex;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Ignoring invalid locale value [" + locale + "]: " + ex.getMessage());
            }
        }
    }
}
