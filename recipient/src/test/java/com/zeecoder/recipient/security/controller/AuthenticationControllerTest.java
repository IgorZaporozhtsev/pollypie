package com.zeecoder.recipient.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.recipient.security.AuthenticationRequest;
import com.zeecoder.recipient.security.AuthenticationResponse;
import com.zeecoder.recipient.security.AuthenticationService;
import com.zeecoder.recipient.security.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @Test
    void testAuthenticate() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        String email = "test@example.com";
        String password = "password123";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token");

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void testRegister() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, password);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token");

        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }
}
