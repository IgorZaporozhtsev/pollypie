package com.zeecoder.kitchen.service;

import com.zeecoder.kafka.OrderEvent;
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
    private final OrderEvent orderEvent;

    public ClientOrder get(UUID orderID) {
        return orderRepository.findById(orderID).orElseThrow(IllegalArgumentException::new);
    }

    public ClientOrder save(ClientOderDto dto) {
        orderEvent.sendMessage(dto.name());

        var order = ClientOrder.builder()
                .name(dto.name())
                .status("CREATED_PENDING")
                .createsAt(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        return orderRepository.save(order);
    }

    public void delete(UUID orderID){
        orderRepository.deleteById(orderID);
    }

    public ClientOrder update(ClientOrder order){
        ClientOrder derived = orderRepository.getReferenceById(order.getOrderID());
        derived.setOrderID(order.getOrderID());
        derived.setName(order.getName());
        derived.setCreatesAt(order.getCreatesAt());
        derived.setClosedAt(order.getClosedAt());
        return orderRepository.save(derived);
    }


}
