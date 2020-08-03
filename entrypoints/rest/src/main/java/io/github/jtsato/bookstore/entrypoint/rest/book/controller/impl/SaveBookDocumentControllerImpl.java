package io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookDocumentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.SaveBookDocumentController;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SaveBookDocumentResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.SaveBookDocumentPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
	@PostMapping(value = "/{bookId}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SaveBookDocumentResponse saveBookDocument(@PathVariable final Long bookId, @RequestPart final MultipartFile file) throws IOException {
    	
        final SaveBookDocumentRequest saveBookDocumentRequest = buildSaveBookDocumentRequest(bookId, file);
        
        log.debug("Starting Controller -> SaveBookDocumentController with {}", JsonConverter.convert(saveBookDocumentRequest));

        final SaveBookDocumentParameters saveBookDocumentParameters = buildSaveBookDocumentParameters(saveBookDocumentRequest);
        
		final BookDocument bookDocument = saveBookDocumentUseCase.saveBookDocument(saveBookDocumentParameters);

        return SaveBookDocumentPresenter.of(bookDocument);	
   }

	private SaveBookDocumentRequest buildSaveBookDocumentRequest(final Long bookId, final MultipartFile file) throws IOException {
		final byte[] content = file.getBytes();
		final Encoder encoder = Base64.getEncoder();        
		final String fileContent = encoder.encodeToString(content);
		return new SaveBookDocumentRequest(bookId, file.getContentType(), FilenameUtils.getExtension(file.getOriginalFilename()), file.getName(), file.getSize(), fileContent);
	}
	
	private SaveBookDocumentParameters buildSaveBookDocumentParameters(final SaveBookDocumentRequest request) {
		return new SaveBookDocumentParameters(request.getBookId(), request.getContentType(), request.getExtension(), request.getName(), request.getSize(), request.getContent());
	}	
	
	@Getter 
	@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE) 
	@EqualsAndHashCode(callSuper = false)
	private static class SaveBookDocumentRequest implements Serializable {

		private static final long serialVersionUID = -4808628565665588269L;

		private final Long bookId;
		
		private final String contentType;
		
		private final String extension;
		
		private final String name;
		
		private final Long size;

		@JsonIgnore
	    private final String content;
	    
	    public SaveBookDocumentRequest(final Long bookId, final String contentType, final String extension, final String name, final Long size, final String content) {  
	        this.bookId = bookId;
	        this.contentType = contentType;
	        this.extension = extension;
	        this.name = name;
	        this.size = size;
	        this.content = content;
	    }
	}
}
