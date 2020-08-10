package io.github.jtsato.bookstore.entrypoint.rest.enumerator.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.domain.response.EnumeratorResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@Component
@RequiredArgsConstructor
public class SearchEnumeratorsPresenter {
    
    private final Locale locale;

    private final MessageSource messageSource;

    public List<EnumeratorResponse> of(final List<Enumerator> enumerators) {
        return enumerators.stream().map(this::of).collect(Collectors.toList());
    }

    private EnumeratorResponse of(final Enumerator enumerator) {
        final String value = messageSource.getMessage(enumerator.getMessageKey(), null, locale);
        return new EnumeratorResponse(enumerator.getDomain(), enumerator.getKey(), value);
    }
}
