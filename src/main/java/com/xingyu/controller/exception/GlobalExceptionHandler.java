package com.xingyu.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex) {
        return responseEntity(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return responseEntity(processFieldErrors(
                ex.getBindingResult().getFieldErrors()), BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return responseEntity(ex.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handle(Exception e) {
        LOG.error("Server Error", e);
        return responseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String processFieldErrors(List<FieldError> fieldErrors) {
        List<String> errorMessages = fieldErrors.stream().map(
                error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String groupedErrorMessages = String.join("; ", errorMessages);
        LOG.debug("Invalid fields found: ".concat(groupedErrorMessages));
        return groupedErrorMessages;
    }

    private static ResponseEntity<Object> responseEntity(String message, HttpStatus status) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(System.currentTimeMillis())
                        .error(message)
                        .status(status.value())
                        .build(),
                status);
    }
}
