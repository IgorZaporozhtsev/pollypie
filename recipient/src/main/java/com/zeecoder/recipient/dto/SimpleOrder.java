package com.zeecoder.recipient.dto;

import java.util.UUID;

public record SimpleOrder(UUID orderID, String description) {
}
