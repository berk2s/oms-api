package com.berk2s.omsapi.infra.exception.handler;

import com.berk2s.omsapi.infra.exception.EntityNotFound;
import com.berk2s.omsapi.infra.exception.ErrorType;
import com.berk2s.omsapi.infra.exception.handler.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), "request.invalid", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("MethodArgumentNotValidException: {}", ex.getMessage());
        var errorList = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), "request.invalid", errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse>  handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException: {}", ex.getSQLException().getMessage());
        return createErrorResponse(ErrorType.SERVER_ERROR.getType(), "server.error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse>  handleNullPointerException(NullPointerException ex) {
        log.warn("NullPointerException: {}", ex.getMessage());
        return createErrorResponse(ErrorType.SERVER_ERROR.getType(), "server.error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFound.class)
    protected ResponseEntity<ErrorResponse>  handleEntityNotFound(EntityNotFound ex) {
        log.warn("EntityNotFound: {}", ex.getMessage());
        return createErrorResponse(ErrorType.NOT_FOUND.getType(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String type, String desc, HttpStatus status) {
        return new ResponseEntity<>(createError(type, desc, desc, List.of()), status);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String type, String desc, List<String> errors, HttpStatus status) {
        return new ResponseEntity<>(createError(type, desc, desc, errors), status);
    }

    private ErrorResponse createError(String type, String desc, String code, List<String> errorMessages) {
        return ErrorResponse.builder()
                .error(type)
                .errorDescription(desc)
                .details(errorMessages)
                .build();
    }
}
