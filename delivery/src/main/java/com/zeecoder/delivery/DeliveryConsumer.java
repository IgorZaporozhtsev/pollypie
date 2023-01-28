package com.zeecoder.delivery;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryConsumer {

    public final DeliveryService deliveryService;

    @KafkaListener(
            topics = "delivery",
            groupId = "groupId"
    )
    void listener(String data) {
        deliveryService.apply(data);
        System.out.println("Kafka listener received data: 🎉🎉🎉" + data);
    }
}
