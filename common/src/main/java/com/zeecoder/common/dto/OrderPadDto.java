package com.zeecoder.common.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderPadDto(UUID orderId, Map<String, Integer> wishes) {
}
