package com.zeecoder.recipient.security.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {

}
