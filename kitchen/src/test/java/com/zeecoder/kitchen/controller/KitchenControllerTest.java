package com.zeecoder.kitchen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.kitchen.dto.TriggerRequest;
import com.zeecoder.kitchen.service.KitchenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = KitchenController.class)
class KitchenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KitchenService kitchenService;

    @Test
    void shouldReturnHttpStatusAccepted() throws Exception {

        var triggerRequest = new TriggerRequest(WorkerState.FREE);

        mockMvc.perform(post("/api/v1/kitchen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(triggerRequest)))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

}