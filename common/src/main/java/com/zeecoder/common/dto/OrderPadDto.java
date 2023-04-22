package com.zeecoder.common.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderPadDto(UUID orderId, List<String> wishes) {
}
