package com.zeecoder.recipient.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
