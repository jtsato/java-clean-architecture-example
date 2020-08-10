package io.github.jtsato.bookstore.core.enumerator.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.usecase.impl.SearchEnumeratorsUseCaseImpl;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;

/**
 * @author Jorge Takeshi Sato  
 */

class SearchEnumeratorsUseCaseTestCase {

    private final SearchEnumeratorsUseCase getBookByIdUseCase = new SearchEnumeratorsUseCaseImpl();
    
    @DisplayName("Successful to search enumerators if exist")
    @Test
    void successfulToSearchEnumeratorsIfNoFilterIsProvided() {

        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.empty(), Optional.empty());
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);

        assertThat(enumerators).isNotNull().isNotEmpty();
        assertThat(enumerators.size()).isEqualTo(2);
    }
    
    @DisplayName("Successful to search enumerators if domain exist")
    @Test
    void successfulToSearchEnumeratorsIfDomainIsProvided() {

        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.of("Gender"), Optional.empty());
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);

        assertThat(enumerators).isNotNull().isNotEmpty();
        assertThat(enumerators.size()).isEqualTo(2);
    }
    
    @DisplayName("Successful to search enumerators if domain and key exist")
    @Test
    void successfulToSearchEnumeratorsIfDomainAndKeyAreProvided() {
        
        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.of("Gender"), Optional.of("Female"));
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);
        
        assertThat(enumerators).isNotNull().isNotEmpty();
        assertThat(enumerators.size()).isOne();
    }
    
    @DisplayName("Successful to search enumerators if domain do not exist")
    @Test
    void successfulToSearchEnumeratorsIfDomainDoNotExist() {
        
        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.of("X"), Optional.empty());
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);
        
        assertThat(enumerators).isNotNull().isEmpty();
        assertThat(enumerators.size()).isZero();
    }
    
    @DisplayName("Successful to search enumerators if key do not exist")
    @Test
    void successfulToSearchEnumeratorsIfKeyDoNotExist() {
        
        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.empty(), Optional.of("X"));
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);
        
        assertThat(enumerators).isNotNull().isEmpty();
        assertThat(enumerators.size()).isZero();
    }
}
