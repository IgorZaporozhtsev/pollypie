package com.zeecoder.recipient.security;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {

}
