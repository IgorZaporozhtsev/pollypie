package com.zeecoder.kitchen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.common.ClientOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderConsumer {

    public final KitchenService kitchenService;
    private final ObjectMapper mapper;

    @KafkaListener(
            topics = "kitchen",
            groupId = "groupId"
    )
    void listener(String record) throws JsonProcessingException {
        ClientOrder clientOrder = mapper.readValue(record, ClientOrder.class);
        log.info("Data from kafka received: 🎉🎉🎉" + clientOrder);

        kitchenService.saveOrder(clientOrder);
    }
}
