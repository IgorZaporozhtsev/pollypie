package com.zeecoder.recipient.dto;

import java.util.UUID;

public record OrderDetailsResponse(UUID id, Integer itemsCount) {
}
