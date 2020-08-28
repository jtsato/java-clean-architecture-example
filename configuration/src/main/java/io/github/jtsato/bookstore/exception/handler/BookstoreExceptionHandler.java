package io.github.jtsato.bookstore.exception.handler;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.jtsato.bookstore.core.exception.InvalidEnumeratorException;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.ParentConstraintException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;
import io.github.jtsato.bookstore.entrypoint.rest.common.HttpResponseStatus;
import io.github.jtsato.bookstore.entrypoint.rest.common.WebRequest;

/**
 * @author Jorge Takeshi Sato
 */

@RestControllerAdvice
public class BookstoreExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebRequest webRequest;

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpResponseStatus handleNotFoundException(final NotFoundException exception, final Locale locale) {
        final String message = messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
        return buildHttpResponseStatus(HttpStatus.NOT_FOUND, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameterException.class)
    public HttpResponseStatus handleInvalidParameterException(final InvalidParameterException exception, final Locale locale) {
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEnumeratorException.class)
    public HttpResponseStatus handleEnumeratorParameterException(final InvalidEnumeratorException exception, final Locale locale) {
        final String message = messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UniqueConstraintException.class)
    public HttpResponseStatus handleUniqueConstraintException(final UniqueConstraintException exception, final Locale locale) {
        final String message = messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParentConstraintException.class)
    public HttpResponseStatus handleParentConstraintException(final ParentConstraintException exception, final Locale locale) {
        final String message = messageSource.getMessage(exception.getMessage(), exception.getArgs(), locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResponseStatus handleConstraintViolationException(final ConstraintViolationException exception, final Locale locale) {
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        final Collector<CharSequence, ?, String> joining = Collectors.joining(", ");
        final String message = violations.stream().map(violation -> messageSource.getMessage(violation.getMessage(), null, locale)).collect(joining);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    public static HttpResponseStatus buildHttpResponseStatus(final HttpStatus httpStatus, final String message, final String path) {
        return new HttpResponseStatus(LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path);
    }
}
