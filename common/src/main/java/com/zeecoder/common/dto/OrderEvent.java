package com.zeecoder.common.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record OrderEvent(String state, String message, OrderPadDto itemDto) {
}
