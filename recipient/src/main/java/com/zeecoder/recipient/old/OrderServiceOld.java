/*
package com.zeecoder.recipient;

import com.zeecoder.domains.*;
import com.zeecoder.kafka.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEvent orderEvent;

    public Optional<ClientOrder> get(UUID orderID) {
        return orderRepository.findById(orderID);
    }

    public void save(ClientOderDto dto) {
        Map<Item, Integer> items = Map.of(
                new Item("Pizza", "USD", BigDecimal.valueOf(20)), 6,
                new Item("Coffee", "USD", BigDecimal.valueOf(8)), 4,
                new Item("Coke", "USD", BigDecimal.valueOf(4)), 2

        );


        var order = ClientOrder.builder()
                //.orderID(UUID.randomUUID())
                .name(dto.name())
                .items(items)
                .state(OrderState.CREATED_PENDING)
                .client(new Client("Bread", "Peterson"))
                .address(new Address("Portland", "Lincoln ave", "3"))
                .createdAt(LocalDateTime.now()
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        orderRepository.save(order);
        orderEvent.sendMessage(dto.name());
    }

    public void delete(UUID orderID){
        orderRepository.deleteById(orderID);
    }

    public ClientOrder update(ClientOrder order){
        ClientOrder derived = orderRepository.getReferenceById(order.getOrderID());
        derived.setOrderID(order.getOrderID());
        derived.setName(order.getName());
        derived.setCreatedAt(order.getCreatedAt());
        derived.setClosedAt(order.getClosedAt());
        return orderRepository.save(derived);
    }



}
*/
