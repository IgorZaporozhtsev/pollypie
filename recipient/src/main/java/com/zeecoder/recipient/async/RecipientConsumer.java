package com.zeecoder.recipient.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeecoder.common.WorkerState;
import com.zeecoder.recipient.service.RecipientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RecipientConsumer {

    public final RecipientService recipientService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "kitchen",
            groupId = "groupId", autoStartup = "true"
    )
    void listener(String status) throws JsonProcessingException {
        var workerState = objectMapper.readValue(status, WorkerState.class);
        if (workerState.equals(WorkerState.FREE)) {
            recipientService.provideNextOrder();
        }
    }
}
