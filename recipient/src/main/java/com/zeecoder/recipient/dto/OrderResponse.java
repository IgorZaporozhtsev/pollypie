package com.zeecoder.recipient.dto;

import com.zeecoder.recipient.domain.ContactDetails;

import java.util.UUID;

public record OrderResponse(UUID orderNumber, ContactDetails contactDetails) {
}
