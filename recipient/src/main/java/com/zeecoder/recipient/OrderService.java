package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.kafka.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderEvent event;

    public void save(ClientOrder order) {
        repository.save(order);
        event.sendMessage(String.valueOf(order));
    }

    public ClientOrder get(UUID orderID) {
        return repository.findById(orderID)
                .orElseThrow(() -> new IllegalArgumentException("There is no"));
    }

    public List<ClientOrder> getAll() {
        return repository.findAll();
    }

    public void delete(UUID orderID) {
        repository.deleteById(orderID);
    }

}
