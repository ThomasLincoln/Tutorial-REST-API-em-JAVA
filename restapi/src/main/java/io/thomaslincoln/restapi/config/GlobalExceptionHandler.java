package io.thomaslincoln.restapi.config;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.thomaslincoln.restapi.services.exceptions.DataBindingViolationException;
import io.thomaslincoln.restapi.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements
 AuthenticationFailureHandler{

  @Value("${server.error.include-exception}")
  private boolean printStackTrace;

  // Manipulação de Exceções de Validação (422)
  @SuppressWarnings("null")
  @Override
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Validation error. Check 'errors' field for details.");
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(
          fieldError.getField(),
          fieldError.getDefaultMessage());
    }
    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }

  // Manipulação de Exceções Genéricas (500)
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllUncaughtException(
      Exception exception,
      WebRequest request) {
    final String errorMessage = "Unknown error occurred";
    log.error(errorMessage, exception);
    return buildErrorResponse(
        exception,
        errorMessage,
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  // Manipulação de violação de integridade de dados (409)
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex,
      WebRequest request) {
    String errorMessage = ex.getMostSpecificCause().getMessage();
    log.error("Failed to save entity with integrity problems: " + errorMessage, ex);
    return buildErrorResponse(
        ex,
        errorMessage,
        HttpStatus.CONFLICT,
        request);
  }

  // Exceções de Violação de Restrição (422)
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException,
      WebRequest request) {
    log.error("Failed to validate element", constraintViolationException);
    return buildErrorResponse(
        constraintViolationException,
        HttpStatus.UNPROCESSABLE_ENTITY,
        request);
  }

  // Exceções de Objeto Não Encontrado (404)
  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleObjectNotFoundException(
      ObjectNotFoundException exception,
      WebRequest request) {
    log.error("Failed to find the requested element", exception);
    return buildErrorResponse(
        exception,
        HttpStatus.NOT_FOUND,
        request);
  }

  // Exceções de Violação de Associação de Dados (409)
  @ExceptionHandler(DataBindingViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDataBindingViolationException(
      DataBindingViolationException exception,
      WebRequest request) {
    log.error("Failed to save entity with associated data", exception);
    return buildErrorResponse(
        exception,
        HttpStatus.CONFLICT,
        request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception,
      HttpStatus httpStatus,
      WebRequest request) {
    return buildErrorResponse(
        exception,
        exception.getMessage(),
        httpStatus,
        request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception,
      String message,
      HttpStatus httpStatus,
      WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
    if (this.printStackTrace) {
      errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
    }
    return ResponseEntity.status(httpStatus).body(errorResponse);
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    Integer status = HttpStatus.FORBIDDEN.value();
    response.setStatus(status);
    response.setContentType("application/json");
    ErrorResponse errorResponse = new ErrorResponse(status, "Email ou senha inválidos");
    response.getWriter().append(errorResponse.toJson());
  }
}
