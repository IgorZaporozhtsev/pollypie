package com.zeecoder.recipient.security;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
