package com.zeecoder.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class Producer<T> {

    public final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, UUID id, T event) {
        kafkaTemplate.send(topic, String.valueOf(id), event);
        log.info("The event was sent to {} topic", event.getClass().getSimpleName(), topic);
    }

    public void sendMessage(String topic, T event) {
        kafkaTemplate.send(topic, event);
        log.info("The event was sent to {} topic", event.getClass().getSimpleName(), topic);
    }

}
