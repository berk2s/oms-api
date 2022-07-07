package com.berk2s.omsapi.infra.exception.handler;

import com.berk2s.omsapi.domain.customer.exception.FakeName;
import com.berk2s.omsapi.domain.inventory.exception.InventoryExists;
import com.berk2s.omsapi.domain.inventory.exception.OutOfQuantityException;
import com.berk2s.omsapi.domain.order.exception.*;
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

    @ExceptionHandler(InventoryExists.class)
    protected ResponseEntity<ErrorResponse>  handleInventoryExists(InventoryExists ex) {
        log.warn("InventoryExists: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FakeName.class)
    protected ResponseEntity<ErrorResponse>  handleFakeName(FakeName ex) {
        log.warn("FakeName: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfQuantityException.class)
    protected ResponseEntity<ErrorResponse>  handleOutOfQuantityException(OutOfQuantityException ex) {
        log.warn("OutOfQuantityException: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyProductState.class)
    protected ResponseEntity<ErrorResponse>  handleEmptyProductState(EmptyProductState ex) {
        log.warn("EmptyProductState: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FakePhoneNumber.class)
    protected ResponseEntity<ErrorResponse>  handleFakePhoneNumber(FakePhoneNumber ex) {
        log.warn("FakePhoneNumber: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InconsistentPrice.class)
    protected ResponseEntity<ErrorResponse>  handleInconsistentPrice(InconsistentPrice ex) {
        log.warn("InconsistentPrice: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCountryCode.class)
    protected ResponseEntity<ErrorResponse>  handleInvalidCountryCode(InvalidCountryCode ex) {
        log.warn("InvalidCountryCode: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPostalCode.class)
    protected ResponseEntity<ErrorResponse>  handleInvalidPostalCode(InvalidPostalCode ex) {
        log.warn("InvalidPostalCode: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityState.class)
    protected ResponseEntity<ErrorResponse>  handleInvalidQuantityState(InvalidQuantityState ex) {
        log.warn("InvalidQuantityState: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderLineNotFound.class)
    protected ResponseEntity<ErrorResponse>  handleOrderLineNotFound(OrderLineNotFound ex) {
        log.warn("OrderLineNotFound: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFound.class)
    protected ResponseEntity<ErrorResponse>  handleOrderLineNotFound(OrderNotFound ex) {
        log.warn("OrderNotFound: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFound.class)
    protected ResponseEntity<ErrorResponse>  handleProductNotFound(ProductNotFound ex) {
        log.warn("ProductNotFound: {}", ex.getMessage());
        return createErrorResponse(ErrorType.INVALID_REQUEST.getType(), ex.getMessage(), HttpStatus.BAD_REQUEST);
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
