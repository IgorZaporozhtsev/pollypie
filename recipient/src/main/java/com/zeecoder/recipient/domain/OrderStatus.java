package com.zeecoder.recipient.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderStatus {
    OPEN("OPEN"),
    IN_PROGRESS("IN_PROGRESS"),
    PREPARED("PREPARED"),
    DELIVERED("DELIVERED");

    @Getter
    private final String code;


}
