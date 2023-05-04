package com.zeecoder.recipient.async;

import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.recipient.service.OrderProvider;
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
public class RecipientConsumer {

    public final OrderProvider orderProvider;

    @KafkaListener(
            topics = "kitchen",
            groupId = "kitchen-group", autoStartup = "true"
    )
    void recipientListener(
            @Payload WorkerState workerState,
            @Header(value = KafkaHeaders.RECEIVED_PARTITION, required = false) final int partition,
            @Header(value = KafkaHeaders.OFFSET, required = false) final long offset,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) final String receivedKey
    ) {
        log.info("{} received partition-offset: {}-{} receivedKey: {}",
                this.getClass().getSimpleName(), partition, offset, receivedKey);
        orderProvider.provide(workerState);
    }
}
