package io.github.jtsato.bookstore.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.FindAuthorsByIdsUseCase;
import io.github.jtsato.bookstore.core.author.usecase.GetAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.RegisterAuthorUseCase;
import io.github.jtsato.bookstore.core.author.usecase.RemoveAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.SearchAuthorsUseCase;
import io.github.jtsato.bookstore.core.author.usecase.UpdateAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.impl.FindAuthorsByIdsUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.impl.GetAuthorByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.impl.RegisterAuthorUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.impl.RemoveAuthorByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.impl.SearchAuthorsUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.impl.UpdateAuthorByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.book.gateway.FindBooksByIdsGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByTitleGateway;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.core.book.gateway.RemoveBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.core.book.usecase.FindBooksByIdsUseCase;
import io.github.jtsato.bookstore.core.book.usecase.GetBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.RegisterBookUseCase;
import io.github.jtsato.bookstore.core.book.usecase.RemoveBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.SearchBooksUseCase;
import io.github.jtsato.bookstore.core.book.usecase.UpdateBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.impl.FindBooksByIdsUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.impl.GetBookByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.impl.RegisterBookUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.impl.RemoveBookByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.impl.SearchBooksUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.impl.UpdateBookByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.common.GetLocalDateTimeImpl;

/**
 * @author Jorge Takeshi Sato  
 */

@EnableSpringDataWebSupport
@Configuration
public class BookstoreConfiguration {

    @Bean
    public RegisterAuthorUseCase registerAuthorUseCase(@Autowired final RegisterAuthorGateway registerAuthorGateway,
                                                       @Autowired final GetAuthorByNameGateway getAuthorByNameGateway) {
        return new RegisterAuthorUseCaseImpl(registerAuthorGateway, getAuthorByNameGateway);
    }

    @Bean
    public GetAuthorByIdUseCase getAuthorByIdUseCase(@Autowired final GetAuthorByIdGateway getAuthorByIdGateway) {
        return new GetAuthorByIdUseCaseImpl(getAuthorByIdGateway);
    }

    @Bean
    public FindAuthorsByIdsUseCase findAuthorsByIdsUseCase(@Autowired final FindAuthorsByIdsGateway findAuthorsByIdsGateway) {
        return new FindAuthorsByIdsUseCaseImpl(findAuthorsByIdsGateway);
    }

    @Bean
    public SearchAuthorsUseCase searchAuthorsUseCase(@Autowired final SearchAuthorsGateway searchAuthorsGateway) {
        return new SearchAuthorsUseCaseImpl(searchAuthorsGateway);
    }

    @Bean
    public UpdateAuthorByIdUseCase updateAuthorByIdUseCase(@Autowired final UpdateAuthorByIdGateway updateAuthorByIdGateway,
                                                           @Autowired final GetAuthorByNameGateway getAuthorByNameGateway) {
        return new UpdateAuthorByIdUseCaseImpl(updateAuthorByIdGateway, getAuthorByNameGateway);
    }

    @Bean
    public RemoveAuthorByIdUseCase removeAuthorByIdUseCase(@Autowired final RemoveAuthorByIdGateway removeAuthorByIdGateway,
                                                           @Autowired final SearchBooksGateway searchBooksGateway) {
        return new RemoveAuthorByIdUseCaseImpl(removeAuthorByIdGateway, searchBooksGateway);
    }

    @Bean
    public RegisterBookUseCase registerBookUseCase(@Autowired final RegisterBookGateway registerBookGateway,
                                                   @Autowired final GetAuthorByIdGateway getAuthorByIdGateway,
                                                   @Autowired final GetBookByTitleGateway getBookByTitleGateway,
                                                   @Autowired final GetLocalDateTime getLocalDateTime) {
        return new RegisterBookUseCaseImpl(registerBookGateway, getAuthorByIdGateway, getBookByTitleGateway, getLocalDateTime);
    }
    
    @Bean
    public GetBookByIdUseCase getBookByIdUseCase(@Autowired final GetBookByIdGateway getBookByIdGateway) {
        return new GetBookByIdUseCaseImpl(getBookByIdGateway);
    }
    
    @Bean
    public FindBooksByIdsUseCase findBooksByIdsUseCase(@Autowired final FindBooksByIdsGateway findBooksByIdsGateway) {
        return new FindBooksByIdsUseCaseImpl(findBooksByIdsGateway);
    }    

    @Bean
    public SearchBooksUseCase searchBooksUseCase(@Autowired final SearchBooksGateway searchBooksGateway) {
        return new SearchBooksUseCaseImpl(searchBooksGateway);
    }

    @Bean
    public UpdateBookByIdUseCase updateBookByIdUseCase(@Autowired final UpdateBookByIdGateway updateBookByIdGateway,
                                                       @Autowired final GetAuthorByIdGateway getAuthorByIdGateway,
                                                       @Autowired final GetBookByTitleGateway getBookByTitleGateway) {
        return new UpdateBookByIdUseCaseImpl(updateBookByIdGateway, getAuthorByIdGateway, getBookByTitleGateway);
    }

    @Bean
    public RemoveBookByIdUseCase removeBookByIdUseCase(@Autowired final RemoveBookByIdGateway removeBookByIdGateway) {
        return new RemoveBookByIdUseCaseImpl(removeBookByIdGateway);
    }
    
    @Bean
    public GetLocalDateTime getLocalDateTime() {
        return new GetLocalDateTimeImpl();
    } 
}
