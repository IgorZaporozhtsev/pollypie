package com.zeecoder.recipient.async;

import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.recipient.service.OrderProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RecipientConsumer {

    public final OrderProvider orderProvider;

    @KafkaListener(
            topics = "kitchen",
            groupId = "kitchen-group", autoStartup = "true"
    )
    void recipientListener(@Payload WorkerState workerState) {
        log.info("Recipient service got Kitchen status is {}", workerState);
        orderProvider.provide(workerState);
    }
}
