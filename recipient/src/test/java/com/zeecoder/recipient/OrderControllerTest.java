package com.zeecoder.recipient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.domains.ClientOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService mockService;

    private ClientOrder order;


    @BeforeEach
    void setUp() {
        order = ClientOrder.builder()
                .orderID(UUID.randomUUID())
                .description("first oder")
                .build();

    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        doNothing().when(mockService).save(order);

        this.mockMvc.perform(
                        post("/api/v1/client-order")
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(order)))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(containsString("Hello, World")));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
