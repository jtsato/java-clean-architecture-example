package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookDocumentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.SaveBookDocumentApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.SaveBookDocumentRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SaveBookDocumentResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.SaveBookDocumentPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.RequiredArgsConstructor;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class SaveBookDocumentController implements SaveBookDocumentApiMethod {

    private static final Logger log = LoggerFactory.getLogger(SaveBookDocumentController.class);

    private final SaveBookDocumentUseCase saveBookDocumentUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{bookId}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SaveBookDocumentResponse saveBookDocument(@PathVariable final Long bookId, @RequestPart final MultipartFile file)
        throws IOException {

        final SaveBookDocumentRequest request = buildSaveBookDocumentRequest(bookId, file);

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> SaveBookDocumentController with {}", jsonRequest);

        final SaveBookDocumentParameters saveBookDocumentParameters = buildSaveBookDocumentParameters(request);

        final BookDocument bookDocument = saveBookDocumentUseCase.execute(saveBookDocumentParameters);

        return SaveBookDocumentPresenter.of(bookDocument);
    }

    private SaveBookDocumentRequest buildSaveBookDocumentRequest(final Long bookId, final MultipartFile file)
        throws IOException {
        final byte[] content = file.getBytes();
        final Encoder encoder = Base64.getEncoder();
        final String fileContent = encoder.encodeToString(content);
        return new SaveBookDocumentRequest(bookId,
                                           file.getContentType(),
                                           FilenameUtils.getExtension(file.getOriginalFilename()),
                                           file.getName(),
                                           file.getSize(),
                                           fileContent);
    }

    private SaveBookDocumentParameters buildSaveBookDocumentParameters(final SaveBookDocumentRequest request) {
        return new SaveBookDocumentParameters(request.getBookId(),
                                              request.getContentType(),
                                              request.getExtension(),
                                              request.getName(),
                                              request.getSize(),
                                              request.getContent());
    }
}
