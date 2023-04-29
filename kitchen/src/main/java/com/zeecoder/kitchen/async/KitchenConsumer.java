package com.zeecoder.kitchen.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.common.dto.OrderEvent;
import com.zeecoder.kitchen.service.KitchenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KitchenConsumer {

    public final KitchenService kitchenService;
    private final ObjectMapper mapper;

    @KafkaListener(
            topics = "recipient",
            groupId = "recipient-group"
    )
    void listener(String order) throws JsonProcessingException, InterruptedException {
        var orderEvent = mapper.readValue(order, OrderEvent.class);
        log.info(orderEvent.getClass().getSimpleName() + " received: -" + orderEvent);
        kitchenService.createItem(orderEvent.itemDto());
    }
}
