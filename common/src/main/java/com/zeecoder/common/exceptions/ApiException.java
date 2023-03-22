package com.zeecoder.common.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

public record ApiException(
        Map<String, String> errorMessage,
        HttpStatus error,
        String exceptionCode,
        ZonedDateTime timestamp
) {

}
