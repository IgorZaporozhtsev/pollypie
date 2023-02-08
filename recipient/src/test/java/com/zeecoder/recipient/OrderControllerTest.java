package com.zeecoder.recipient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.Item;
import com.zeecoder.domains.OrderState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipientService mockService;

    private ClientOrder order;
    @MockBean
    private SimpleOrderDTOMapper simpleOrderDTOMapper;

    private final UUID orderID = UUID.randomUUID();

    @BeforeEach
    void setUp() {

        ArrayList<Item> items = new ArrayList<>();
        Item pizza = Item.builder()
                .itemID(UUID.randomUUID())
                .name("Pizza")
                .adds(new ArrayList<>()).build();

        items.add(pizza);

        order = ClientOrder.builder()
                .orderID(orderID)
                .description("first oder")
                .state(OrderState.OPEN)
                .items(items)
                .build();

    }

    @Test
    public void shouldReturnStatusOkForAll() throws Exception {
        this.mockMvc.perform(get("/api/v1/client-order")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOk() throws Exception {
        when(mockService.get(orderID)).thenReturn(Optional.ofNullable(order));

        this.mockMvc.perform(get("/api/v1/client-order/{orderID}", orderID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusCreated() throws Exception {
        doNothing().when(mockService).save(order);

        this.mockMvc.perform(
                        post("/api/v1/client-order")
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(order)))
                .andDo(print())
                .andExpect(status().isCreated());
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
