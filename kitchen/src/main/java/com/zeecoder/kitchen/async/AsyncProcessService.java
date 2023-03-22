package com.zeecoder.kitchen.async;

import com.zeecoder.common.ClientOrder;
import com.zeecoder.common.OrderRepository;
import com.zeecoder.common.OrderState;
import com.zeecoder.common.exceptions.ApiRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class AsyncProcessService {

    private final OrderRepository orderRepository;

    @Async
    public CompletableFuture<String> process(UUID orderID) {

        String state = orderRepository.findById(orderID)
                .map(ClientOrder::getState)
                .map(OrderState::toString)
                .orElseThrow(() -> new ApiRequestException(orderID.toString(), "GEEX001"));

        log.info("state was received");

        return CompletableFuture.completedFuture(state);
    }

}
