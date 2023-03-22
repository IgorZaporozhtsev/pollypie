package com.zeecoder.recipient.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange("kitchen/")
public interface KitchenClient {
    @GetExchange(value = "{orderID}")
    String get(@PathVariable UUID orderID);
}
