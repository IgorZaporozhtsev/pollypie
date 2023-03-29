package com.zeecoder.recipient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.common.ClientOrder;
import com.zeecoder.common.Item;
import com.zeecoder.recipient.service.RecipientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipientControllerMvcTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private RecipientService service;
    private UUID orderID;
    private Item celentano;
    private Item florence;
    private ClientOrder orderNumber40;
    private ClientOrder orderNumber41;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        orderID = UUID.randomUUID();

        celentano = Item.builder()
                .itemID(UUID.randomUUID())
                .name("Pizza Celentano")
                .amount(2)
                .build();

        florence = Item.builder()
                .itemID(UUID.randomUUID())
                .name("Pizza Florence")
                .amount(1)
                .build();

        orderNumber40 = ClientOrder.builder()
                .orderID(orderID)
                .description("generated order, number 40")
                .items(List.of(celentano))
                .build();

        orderNumber41 = ClientOrder.builder()
                .orderID(orderID)
                .description("generated order, number 41")
                .items(List.of(florence))
                .build();
    }

    @Test
    void testGet() throws Exception {

        when(service.get(orderID)).thenReturn(Optional.of(orderNumber40));

        ResultActions perform = mockMvc.perform(get("/api/v1/client-order/{orderID}", orderID));
        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderID").value(orderID.toString()));
    }


    @Test
    public void testGetAll() throws Exception {

        Page<ClientOrder> page = new PageImpl<>(List.of(orderNumber40, orderNumber41));

        given(service.getOrders(any(Pageable.class))).willReturn(page);

        // perform the GET request to the controller
        mockMvc.perform(get("/api/v1/client-order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].orderID").value(orderID.toString()))
                .andExpect(jsonPath("$.content[1].orderID").value(orderID.toString()));

        // verify that the service method was called with the correct arguments
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).getOrders(pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();
        assertEquals("orderID", pageable.getSort().getOrderFor("orderID").getProperty());
        assertEquals(Sort.Direction.ASC, pageable.getSort().getOrderFor("orderID").getDirection());
        assertEquals(5, pageable.getPageSize());
    }


    @Test
    void testSaveOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(orderNumber40);

        mockMvc.perform(post("/api/v1/client-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testNewItem() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(celentano);

        mockMvc.perform(post("/api/v1/client-order/{orderId}", orderID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "internal:delete")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/client-order/{orderId}", orderID))
                .andExpect(status().isNoContent());
    }
}