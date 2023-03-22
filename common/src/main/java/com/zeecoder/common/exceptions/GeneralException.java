package com.zeecoder.common.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public enum GeneralException {
    USER_ALREADY_EXIST(
            "Order already exist",
            HttpStatus.BAD_REQUEST,
            "GEEX002",
            ZonedDateTime.now(ZoneId.of("Z"))
    ),

    USER_NOT_FOUND(
            "Order is not present",
            HttpStatus.NOT_FOUND,
            "GEEX001",
            ZonedDateTime.now(ZoneId.of("Z"))
    ),

    VALID_EXCEPTION(
            "Validation failed for argument",
            HttpStatus.BAD_REQUEST,
            "MethodArgumentNotValidException",
            ZonedDateTime.now(ZoneId.of("Z"))
    );

    public final String message;
    public final HttpStatus httpStatus;
    public final String exceptionCode;
    public final ZonedDateTime timestamp;

    GeneralException(String message, HttpStatus httpStatus, String exceptionCode, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.exceptionCode = exceptionCode;
        this.timestamp = timestamp;
    }
}
