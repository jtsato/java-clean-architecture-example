package io.github.jtsato.bookstore.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    private static final Logger LOGGER = LogManager.getLogger(BookstoreExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebRequest webRequest;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpResponseStatus handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception, final Locale locale) {
        final String message = messageSource.getMessage("exception.request.body.is.invalid.or.missing", null, locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public HttpResponseStatus handleBindException(final BindException exception, final Locale locale) {
        final Object[] args = {StringUtils.substringBetween(exception.getMessage(), "property '", "'")};
        final String message = messageSource.getMessage("exception.field.format", args, locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public HttpResponseStatus handleHibernateConstraintViolationException(final org.hibernate.exception.ConstraintViolationException exception, final Locale locale) {
        final String message = messageSource.getMessage(exception.getMessage(), null, locale);
        return buildHttpResponseStatus(HttpStatus.BAD_REQUEST, message, webRequest.getPath());
    }    
    
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResponseStatus handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception, final Locale locale) {
        final List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        final String message = errors.stream().map(error -> messageSource.getMessage(error, locale)).collect(Collectors.joining(", "));
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
    
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public HttpResponseStatus handleAccessDeniedException(final AccessDeniedException exception, final Locale locale) {
        final String message = messageSource.getMessage("exception.access.denied", null, locale);
        return buildHttpResponseStatus(HttpStatus.FORBIDDEN, message, webRequest.getPath());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpResponseStatus handleException(final Exception exception, final Locale locale) {
        LOGGER.error("Exception: {}", exception.getMessage());
        final String message = messageSource.getMessage("exception.unexpected", null, locale);
        return buildHttpResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, message, webRequest.getPath());
    }

    public static HttpResponseStatus buildHttpResponseStatus(final HttpStatus httpStatus, final String message, final String path) {
        return new HttpResponseStatus(LocalDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, path);
    }
}
