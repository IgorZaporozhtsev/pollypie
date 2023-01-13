package com.zeecoder.kafka;

import com.zeecoder.delivery.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryListeners {

    public final DeliveryService deliveryService;

    @KafkaListener(
            topics = "delivery",
            groupId = "groupId"
    )
    void listener(String data) {
        deliveryService.process(data);
        System.out.println("Listener received data: 🎉🎉🎉" + data);
    }
}
