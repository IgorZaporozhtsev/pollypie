package com.zeecoder.common.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> methodArgumentNotValid(MethodArgumentNotValidException exception) {

        var status = Arrays.stream(GeneralException.values())
                //TODO check null safe equals
                .filter(it -> Objects.equals(it.exceptionCode, exception.getClass().getSimpleName()))
                .findFirst()
                .orElse(GeneralException.VALID_EXCEPTION);

        var fields = exception.getBindingResult().getFieldErrors();

        var causeFields = fields.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        var exceptionInfo = new ApiException(
                causeFields,
                status.httpStatus,
                status.exceptionCode,
                status.timestamp);

        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }
}
