package com.zeecoder.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEvent<T> {

    public final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(T order) {
        kafkaTemplate.send("kitchen", order);
    }
}
