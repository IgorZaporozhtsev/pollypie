package com.zeecoder.common.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiException> orderNotFound(ApiRequestException exception
    ) {

        var status = Arrays.stream(GeneralException.values())
                //TOTO check null safe equals
                .filter(it -> Objects.equals(it.exceptionCode, exception.exceptionCode))
                .findFirst()
                .orElseThrow();

        var exceptionInfo =
                new ApiException(Map.of(exception.message, status.message),
                        status.httpStatus, status.exceptionCode, status.timestamp);

        return new ResponseEntity<>(exceptionInfo, status.httpStatus);
    }


}
