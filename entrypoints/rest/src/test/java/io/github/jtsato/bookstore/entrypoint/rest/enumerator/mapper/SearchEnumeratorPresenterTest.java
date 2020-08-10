package io.github.jtsato.bookstore.entrypoint.rest.enumerator.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.domain.response.EnumeratorResponse;

/**
 * @author Jorge Takeshi Sato  
 */

class SearchEnumeratorsPresenterTest {

    @Mock
    private final MessageSource messageSource = Mockito.mock(MessageSource.class);

    private final Locale locale = Locale.US;

    @InjectMocks
    private final SearchEnumeratorsPresenter presenter = new SearchEnumeratorsPresenter(locale, messageSource);

    @DisplayName("Successful to search enumerators")
    @Test
    void successfulToSearchEnumerators() {

        when(messageSource.getMessage(Gender.MALE.getMessageKey(), null, locale)).thenReturn("Male");
        when(messageSource.getMessage(Gender.FEMALE.getMessageKey(), null, locale)).thenReturn("Female");

        final List<EnumeratorResponse> responses = presenter.of(dummyEnumerators());
        assertThat(responses).isNotNull().isNotEmpty();
        assertThat(responses.size()).isEqualTo(2);

        final EnumeratorResponse response1 = responses.get(0);
        assertThat(response1).isNotNull();
        assertThat(response1.getDomain()).isEqualTo("Gender");
        assertThat(response1.getKey()).isEqualTo("MALE");
        assertThat(response1.getValue()).isEqualTo("Male");

        final EnumeratorResponse response2 = responses.get(1);
        assertThat(response2).isNotNull();
        assertThat(response2.getDomain()).isEqualTo("Gender");
        assertThat(response2.getKey()).isEqualTo("FEMALE");
        assertThat(response2.getValue()).isEqualTo("Female");
    }

    private List<Enumerator> dummyEnumerators() {
        final List<Enumerator> elements = new ArrayList<>(2);
        elements.add(new Enumerator("Gender", Gender.MALE.name(), Gender.MALE.getMessageKey()));
        elements.add(new Enumerator("Gender", Gender.FEMALE.name(), Gender.FEMALE.getMessageKey()));
        return elements;
    }
}
