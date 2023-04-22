package com.zeecoder.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class Producer<T> {

    public final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T event) {
        log.info(String.format("Start to sent event %s", event));
        var message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
        log.info(String.format("Event %s was sent to %s topic", event.getClass().getSimpleName(), topic));
    }

}
