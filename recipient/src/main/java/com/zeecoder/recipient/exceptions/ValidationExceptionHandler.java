package com.zeecoder.recipient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> methodArgumentNotValid(MethodArgumentNotValidException exception) {

        var status = Arrays.stream(GeneralException.values())
                //TOTO check null safe equals
                .filter(it -> Objects.equals(it.exceptionCode, exception.getClass().getSimpleName()))
                .findFirst()
                .orElseThrow();

        var exceptionInfo = new ApiException(
                String.join(" ", status.message),
                status.httpStatus,
                status.exceptionCode,
                status.timestamp);

        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }
}
