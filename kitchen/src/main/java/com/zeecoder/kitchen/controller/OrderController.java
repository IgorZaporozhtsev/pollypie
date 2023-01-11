package com.zeecoder.kitchen.controller;

import com.zeecoder.kitchen.domain.ClientOderDto;
import com.zeecoder.kitchen.domain.ClientOrder;
import com.zeecoder.kitchen.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    public final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "{orderID}")
    public ClientOrder getOrder(@PathVariable("orderID") UUID orderID){
        return orderService.getOrder(orderID);
    }


    @PostMapping
    public void saveOrder(@RequestBody ClientOderDto dto){
        orderService.saveOrder(dto);
    }
}
