package com.zeecoder.kitchen.datarecords;

import java.io.Serial;

public class JsonParsingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4072490871556433194L;

    public JsonParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
