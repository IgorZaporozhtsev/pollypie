package com.zeecoder.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEvent {


    public final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String orderName){
        kafkaTemplate.send("kitchen", "your order is " + orderName);
    }
}
