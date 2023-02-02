package com.zeecoder.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderState {
    OPEN("O"),
    PENDING("P"),
    FINISHED("F");

    @Getter
    private final String code;


}
