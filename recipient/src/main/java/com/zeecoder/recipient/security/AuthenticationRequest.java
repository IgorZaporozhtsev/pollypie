package com.zeecoder.recipient.security;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {
}
