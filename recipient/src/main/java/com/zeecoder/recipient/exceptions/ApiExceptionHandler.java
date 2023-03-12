package com.zeecoder.recipient.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiRecipientException.class)
    public ResponseEntity<ApiException> orderNotFound(ApiRecipientException exception
    ) {

        var status = Arrays.stream(GeneralException.values())
                //TOTO check null safe equals
                .filter(it -> Objects.equals(it.exceptionCode, exception.exceptionCode))
                .findFirst()
                .orElseThrow();

        var exceptionInfo =
                new ApiException(String.join(" ", status.message, exception.message),
                        status.httpStatus, status.exceptionCode, status.timestamp);

        return new ResponseEntity<>(exceptionInfo, status.httpStatus);
    }


}
