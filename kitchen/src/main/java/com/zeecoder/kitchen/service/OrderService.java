package com.zeecoder.kitchen.service;

import com.zeecoder.kafka.TestComponent;
import com.zeecoder.kitchen.domain.ClientOderDto;
import com.zeecoder.kitchen.domain.ClientOrder;
import com.zeecoder.kitchen.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TestComponent testComponent;

    public ClientOrder getOrder(UUID orderID) {
        return null;
    }

    public void saveOrder(ClientOderDto dto) {
        testComponent.sendMessage(dto.name());

        var order = ClientOrder.builder()
                .name(dto.name())
                .status("PENDING")
                .createsAt(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        orderRepository.save(order);
    }
}
