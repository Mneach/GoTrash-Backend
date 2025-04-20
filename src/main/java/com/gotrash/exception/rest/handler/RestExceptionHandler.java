package com.gotrash.exception.rest.handler;

import com.gotrash.exception.rest.BadRequestException;
import com.gotrash.exception.rest.ForbiddenAccessException;
import com.gotrash.exception.rest.InternalServerException;
import com.gotrash.exception.rest.description.RestErrorDescription;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Default GOTRASH-BACKEND REST exception handler controller advice.
 * You can provide your own controller advice by extending this class.
 */
@ControllerAdvice
@Component("gotrashBackendDefaultRestExceptionHandler")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger HANDLER_LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ResponseStatusException.class)
  protected ResponseEntity<Object> handleException(ResponseStatusException e, WebRequest webRequest) {
    RestErrorDescription restErrorDescription = new RestErrorDescription(e, webRequest);
    handleLogging(e);
    return handleExceptionInternal(e, restErrorDescription, new HttpHeaders(), e.getStatusCode(), webRequest);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException e, WebRequest webRequest) {
    ForbiddenAccessException forbiddenAccessException = new ForbiddenAccessException("You are forbidden from accessing this API");
    return this.handleException(forbiddenAccessException, webRequest);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
    BadRequestException badRequestException = new BadRequestException(e.getMessage());
    RestErrorDescription restErrorDescription = new RestErrorDescription(badRequestException, request);
    handleLogging(badRequestException);
    return this.handleExceptionInternal(badRequestException, restErrorDescription, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  // INFO: Handle unknown exception as InternalServerException
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleUnhandledException(Exception e, WebRequest webRequest) {
    InternalServerException internalServerException = new InternalServerException("Unhandled Exception! Exception: " + e.getClass().getSimpleName(), e);
    return handleException(internalServerException, webRequest);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, handleValidationErrorMessages(e), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return handleException(new ResponseStatusException(status, e.getMessage(), e), request);
  }

  protected void handleLogging(ResponseStatusException e) {
    String errorMessage = "REST API exception occurred with status code: " + e.getStatusCode().value();
    if (e.getStatusCode().is4xxClientError()) {
      HANDLER_LOGGER.warn(errorMessage, e);
    } else {
      HANDLER_LOGGER.error(errorMessage, e);
    }
  }

  protected String handleValidationErrorMessages(MethodArgumentNotValidException e) {
    StringBuilder sb = new StringBuilder("Validation failed for arguments:");
    for (ObjectError error : e.getBindingResult().getAllErrors()) {
      sb.append(" ");
      if (error instanceof FieldError fieldError) {
        sb.append("[Field name: ").append(fieldError.getField()).append(", message: ").append(fieldError.getDefaultMessage()).append("]");
      } else {
        sb.append("[").append(error).append("]");
      }
    }
    return sb.toString();
  }
}
