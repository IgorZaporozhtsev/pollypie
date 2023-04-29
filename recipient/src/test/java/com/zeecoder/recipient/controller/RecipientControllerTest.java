package com.zeecoder.recipient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.recipient.domain.ContactDetails;
import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.Product;
import com.zeecoder.recipient.dto.Menu;
import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.MenuRequest;
import com.zeecoder.recipient.service.RecipientService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecipientController.class)
@AutoConfigureMockMvc(addFilters = false) //ignore any filters such Spring Security Filters
class RecipientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RecipientService service;

    private UUID orderId;
    private Order orderNumber40;
    private MenuRequest menuRequest;
    private MenuDetailsResponse menuDetailsResponse;
    private final String requestMapping = "/api/v1/client-order";

    @BeforeEach
    void initEach() {
        orderId = UUID.randomUUID();

        var contactDetails = ContactDetails.builder()
                .phoneNumber("+389073456352")
                .lastName("Gregor")
                .firstName("Magvayer")
                .address("St.Middle of anywhere")
                .email("gr@gmail.com")
                .build();

        var margarita = Menu.builder().dish("Margarita").count(2).build();
        var beer = Menu.builder().dish("Beer").count(1).build();

        menuRequest = MenuRequest.builder()
                .title("new Menu request")
                .menuList(List.of(margarita, beer))
                .contactDetails(contactDetails).build();
        menuDetailsResponse = new MenuDetailsResponse(orderId, contactDetails);

        var pizzaCelentano = Product.builder()
                .id(UUID.randomUUID())
                .productName("Pizza Celentano")
                .price(BigDecimal.valueOf(10))
                .sku(UUID.randomUUID().toString())
                .build();

        Item celentanoItem = Item.builder()
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

        Item florenceItem = Item.builder()
                .id(UUID.randomUUID())
                .quantity(1)
                .product(pizzaFlorence)
                .build();


        orderNumber40 = Order.builder()
                .id(orderId)
                .title("It's important order")
                .items(List.of(celentanoItem))
                .contactDetails(contactDetails)
                .build();

        Order orderNumber41 = Order.builder()
                .id(orderId)
                .title("It's regular order")
                .items(List.of(florenceItem))
                .contactDetails(contactDetails)
                .build();
    }

    @Test
    void shouldReturnStatusCreatedForSaveOrder() throws Exception {
        given(service.save(any(MenuRequest.class))).willReturn(menuDetailsResponse);

        var json = objectMapper.writeValueAsString(menuRequest);

        System.out.println("requestContent: " + json);
        mockMvc.perform(post(requestMapping)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                // .andExpect(content().string(objectMapper.writeValueAsString(menuResponse)))
                .andExpect(jsonPath("$.id").value(orderNumber40.getId().toString()))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnStatusOkForGetOrderById() throws Exception {
        given(service.getById(orderId)).willReturn(menuDetailsResponse);

        mockMvc.perform(get(requestMapping + "/{id}", orderId)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(content().string(objectMapper.writeValueAsString(menuDetailsResponse)));
    }

    @Test
    void shouldReturnStatusOkForGetAllOrders() throws Exception {

        Page<MenuDetailsResponse> page = new PageImpl<>(List.of(menuDetailsResponse));

        given(service.getOrders(any(Pageable.class))).willReturn(page);

        // perform the GET request to the controller
        mockMvc.perform(get(requestMapping))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
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
    void shouldReturnStatusDeletedForOrder() throws Exception {
        mockMvc.perform(delete(requestMapping + "/{orderId}", orderId))
                .andExpect(status().isNoContent());
    }
}