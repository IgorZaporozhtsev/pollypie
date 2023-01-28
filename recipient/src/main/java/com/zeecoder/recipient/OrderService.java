package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.kafka.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    OrderRepository repository;
    OrderEvent event;

    public void save(ClientOrder order) {
        repository.save(order);
        event.sendMessage(String.valueOf(order));
    }
}
