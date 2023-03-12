package com.zeecoder.recipient.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(
        String errorMessage,
        HttpStatus error,
        String exceptionCode,
        ZonedDateTime timestamp
) {

}
