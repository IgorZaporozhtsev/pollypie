package com.zeecoder.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderState {
    OPEN("O"),
    PREPARED("P"),
    FINISHED("F");

    @Getter
    private final String code;


}
