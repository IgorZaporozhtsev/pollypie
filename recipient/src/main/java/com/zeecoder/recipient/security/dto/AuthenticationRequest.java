package com.zeecoder.recipient.security.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {
}
