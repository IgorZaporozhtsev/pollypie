package com.zeecoder.common.exceptions;

import java.io.Serial;

public class ApiRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5061597808822711140L;

    public final String exceptionCode;
    public final String message;

    public ApiRequestException(String message, String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}
