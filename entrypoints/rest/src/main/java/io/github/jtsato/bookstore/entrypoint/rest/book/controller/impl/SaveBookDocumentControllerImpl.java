package io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookDocumentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.SaveBookDocumentController;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SaveBookDocumentResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.SaveBookDocumentPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * A EntryPoint follows these steps:
 * 
 * - Maps HTTP requests to Java objects
 * - Performs authorization checks
 * - Maps input to the input model of the use case
 * - Calls the use case
 * - Maps the output of the use case back to HTTP Returns an HTTP response
 */

/**
 * @author Jorge Takeshi Sato  
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class SaveBookDocumentControllerImpl implements SaveBookDocumentController {

    private final SaveBookDocumentUseCase saveBookDocumentUseCase;

    @LogExecutionTime    
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
	public SaveBookDocumentResponse saveBookDocument(@RequestParam final Long bookId, @RequestPart final MultipartFile file) {
    	
        log.debug("Starting Controller -> SaveBookDocumentController with {}", JsonConverter.convert(bookId));

        final String content = getFileContent(file);
        
		final SaveBookDocumentParameters parameters = new SaveBookDocumentParameters(bookId, content);

        final BookDocument bookContent = saveBookDocumentUseCase.saveBookDocument(parameters);

        return SaveBookDocumentPresenter.of(bookContent);	
   }

	private String getFileContent(final MultipartFile file) {

		try {
			
			if (file == null) {
				throw new InvalidParameterException("validation.book.document.content.null");
			}
			
			final byte[] content = file.getBytes();

			if (content.length == 0) {
				throw new InvalidParameterException("validation.book.document.content.blank");
			}
			
			final Encoder encoder = Base64.getEncoder();        

			return encoder.encodeToString(content);

		} catch (IOException ioException) {
			
			log.error("It was not possible to read the file content. ", ioException);

			throw new InvalidParameterException("validation.book.document.content.invalid");
		}
	}
}
