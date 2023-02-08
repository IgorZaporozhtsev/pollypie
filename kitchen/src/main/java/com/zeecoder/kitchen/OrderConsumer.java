package com.zeecoder.kitchen;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderConsumer {

    public final KitchenService kitchenService;

    @KafkaListener(
            topics = "kitchen",
            groupId = "groupId"
    )
    void listener(String data) {
        kitchenService.apply(data);
        System.out.println("Kitchen service received data from recipient: 🎉🎉🎉" + data);
    }
}
