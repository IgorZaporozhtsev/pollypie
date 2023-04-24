package com.zeecoder.recipient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.recipient.controller.RecipientController;
import com.zeecoder.recipient.domain.ContactDetails;
import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.Product;
import com.zeecoder.recipient.dto.OrderResponse;
import com.zeecoder.recipient.service.RecipientService;
import com.zeecoder.recipient.util.SimpleOrderDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecipientController.class)
@AutoConfigureMockMvc(addFilters = false) //ignore any filters such Spring Security Filters
class RecipientControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipientService service;
    @MockBean
    private SimpleOrderDTOMapper simpleOrderDTOMapper;

    private UUID orderId;
    private Item celentanoItem;
    private Item florenceItem;
    private Order orderNumber40;
    private Order orderNumber41;
    private final String requestMapping = "/api/v1/client-order";

    @BeforeEach
    void initEach() {
        orderId = UUID.randomUUID();

        var pizzaCelentano = Product.builder()
                .id(UUID.randomUUID())
                .productName("Pizza Celentano")
                .price(BigDecimal.valueOf(10))
                .sku(UUID.randomUUID().toString())
                .build();

        celentanoItem = Item.builder()
                .id(UUID.randomUUID())
                .quantity(2)
                .product(pizzaCelentano)
                .build();

        var pizzaFlorence = Product.builder()
                .id(UUID.randomUUID())
                .productName("Pizza Florence")
                .price(BigDecimal.valueOf(12))
                .sku(UUID.randomUUID().toString())
                .build();

        florenceItem = Item.builder()
                .id(UUID.randomUUID())
                .quantity(1)
                .product(pizzaFlorence)
                .build();

        var contactDetails = ContactDetails.builder()
                .phoneNumber("+389073456352")
                .lastName("Gregor")
                .firstName("Magvayer")
                .address("St.Middle of anywhere")
                .email("gr@gmail.com")
                .build();

        orderNumber40 = Order.builder()
                .id(orderId)
                .title("It's important order")
                .items(List.of(celentanoItem))
                .contactDetails(contactDetails)
                .build();

        orderNumber41 = Order.builder()
                .id(orderId)
                .title("It's regular order")
                .items(List.of(florenceItem))
                .contactDetails(contactDetails)
                .build();
    }

    @Test
    void shouldReturnStatusOkForGetOrderById() throws Exception {
        given(service.getById(orderId)).willReturn(Optional.of(orderNumber40));

        mockMvc.perform(get(requestMapping + "/{id}", orderId)
                        .contentType("application/json"))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(content().string(objectMapper.writeValueAsString(orderNumber40)));
    }

    @Test
    void shouldReturnStatusOkForGetAllOrders() throws Exception {

        Page<Order> page = new PageImpl<>(List.of(orderNumber40, orderNumber41));

        given(service.getOrders(any(Pageable.class))).willReturn(page);

        // perform the GET request to the controller
        mockMvc.perform(get(requestMapping))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(page)));

        // verify that the service method was called with the correct arguments
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).getOrders(pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();
        assertEquals("id", pageable.getSort().getOrderFor("id").getProperty());
        assertEquals(Sort.Direction.ASC, pageable.getSort().getOrderFor("id").getDirection());
        assertEquals(5, pageable.getPageSize());
    }


    @Test
    void shouldReturnStatusCreatedForSaveOrder() throws Exception {

        given(service.save(any(Order.class))).willReturn(orderNumber40);
        var orderResponse = new OrderResponse(orderId, orderNumber40.getContactDetails());

        mockMvc.perform(post(requestMapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderNumber40)))
                .andDo(print())
                .andExpect(content().string(objectMapper.writeValueAsString(orderResponse)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnStatusCreatedForNewItem() throws Exception {
        willDoNothing().given(service).addNewItemToOrder(celentanoItem, UUID.randomUUID());

        mockMvc.perform(post(requestMapping + "/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(celentanoItem)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnStatusDeletedForOrder() throws Exception {
        mockMvc.perform(delete(requestMapping + "/{orderId}", orderId))
                .andExpect(status().isNoContent());
    }
}