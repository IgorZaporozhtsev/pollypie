package com.zeecoder.kitchen.async;

import com.zeecoder.common.dto.OrderEvent;
import com.zeecoder.kitchen.service.KitchenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KitchenConsumer {

    public final KitchenService kitchenService;

    @KafkaListener(
            topics = "recipient",
            groupId = "recipient-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    void kitchenListener(
            @Payload OrderEvent orderEvent,
            @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) final int partition,
            @Header(value = KafkaHeaders.OFFSET, required = false) final long offset,
            @Header(value = KafkaHeaders.RECEIVED_KEY) final String receivedKey
    ) throws InterruptedException {
        log.info("{} received partition: {} offset: {} receivedKey: {}",
                this.getClass().getSimpleName(), partition, offset, receivedKey);
        kitchenService.createItem(orderEvent.itemDto());

    }
}
