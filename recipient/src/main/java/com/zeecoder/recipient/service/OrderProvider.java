package com.zeecoder.recipient.service;

import com.zeecoder.common.dto.OrderEvent;
import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.kafka.Producer;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.zeecoder.common.dto.WorkerState.FREE;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProvider {

    private final OrderService orderService;
    private final Producer<OrderEvent> producer;
    private WorkerState workerState;

    public void provide(WorkerState workerState) {
        this.workerState = workerState;
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processNextOrder() {
        log.info("Scheduler processNextOrder is running....");
        if (FREE.equals(workerState)) {
            log.info("Starting to seek an order....");
            orderService.findOpenedOrder(OrderStatus.OPEN.name())
                    .ifPresentOrElse(this::process,
                            () -> log.info("Oops, perhaps you don't have orders anymore"));
        }
    }

    public void process(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);

        var orderPadDto = OrderPadDto.builder()
                .orderId(order.getId())
                .wishes(order.getOrderDefinitions())
                .build();

        producer.sendMessage("recipient", order.getId(), assembleOrderEvent(orderPadDto));
    }

    private OrderEvent assembleOrderEvent(OrderPadDto orderPadDto) {
        return new OrderEvent("OPEN", "order was opened at" + LocalDate.now(), orderPadDto);
    }

}


