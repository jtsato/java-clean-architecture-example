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

class SearchEnumeratorPresenterTest {
    
    @Mock
    private final MessageSource messageSource = Mockito.mock(MessageSource.class);

    @InjectMocks
    private final SearchEnumeratorsPresenter presenter = new SearchEnumeratorsPresenter(messageSource);
    
    @DisplayName("Successful to find authors by IDs, when at least one is found")
    @Test
    void successfulToFindAuthorsByIdsIfFound() {
        
        when(messageSource.getMessage(Gender.MALE.getMessageKey(), null, Locale.US)).thenReturn("Male");
        when(messageSource.getMessage(Gender.FEMALE.getMessageKey(), null, Locale.US)).thenReturn("Female");
        
        final List<EnumeratorResponse> enumeratorResponses = presenter.of(dummyEnumerators());
        assertThat(enumeratorResponses).isNotNull().isNotEmpty();
        assertThat(enumeratorResponses.size()).isEqualTo(2);
    }
    
    private List<Enumerator> dummyEnumerators() {
        final List<Enumerator> elements = new ArrayList<>(2);
        elements.add(new Enumerator("Gender", Gender.MALE.name(), Gender.MALE.getMessageKey()));
        elements.add(new Enumerator("Gender", Gender.FEMALE.name(), Gender.FEMALE.getMessageKey()));
        return elements;
    }
}
