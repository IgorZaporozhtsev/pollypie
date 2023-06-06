package com.zeecoder.kitchen.webclient.config;

import java.io.Serial;

public class ExternalServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6124155757569487740L;

    public ExternalServiceException(String message) {
        super(message);
    }
}
