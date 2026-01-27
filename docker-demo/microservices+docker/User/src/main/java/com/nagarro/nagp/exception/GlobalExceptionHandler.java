package com.nagarro.nagp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        LOG.info("caught handleValidationExceptions:{}", ex.getLocalizedMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), errors.toString());

    }

    @ExceptionHandler(MandatoryDataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMandatoryDataException(MandatoryDataNotFoundException ex) {
        LOG.info("caught handleMandatoryDataException:{}", ex.getLocalizedMessage());
        return ErrorResponse.builder().message(ex.getLocalizedMessage()).statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value())).build();
    }

    @ExceptionHandler(value = GenericException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAllGenericException(GenericException ex) {
        LOG.info("caught handleAllGenericException:{}", ex.getLocalizedMessage());
        return ErrorResponse.builder().statusCode(ex.getCode()).message(ex.getMessage()).build();
    }


    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleArithmeticException(ArithmeticException ex) {
        LOG.info("caught handleArithmeticException:{}", ex.getLocalizedMessage());
        return ErrorResponse.builder().message(ex.getLocalizedMessage()).statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value())).build();
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(RuntimeException ex) {
        LOG.info("caught handleNullPointerException:{}", ex.getLocalizedMessage());
        return ErrorResponse.builder().message(ex.getLocalizedMessage()).statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())).build();

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        LOG.info("caught handleException:{}", ex.getLocalizedMessage());
        return ErrorResponse.builder().statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())).message(ex.getLocalizedMessage()).build();
    }


}