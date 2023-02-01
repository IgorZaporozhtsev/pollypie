package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.Item;
import com.zeecoder.kafka.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderEvent event;

    public void save(ClientOrder order) {
        ClientOrder savedOrder = orderRepository.save(order);
        event.sendMessage(String.valueOf(savedOrder));
    }

    public Optional<ClientOrder> get(UUID orderID) {
        return orderRepository.findById(orderID);
    }

    public List<ClientOrder> getAll() {
        return orderRepository.findAll();
    }

    public void delete(UUID orderID) {
        orderRepository.deleteById(orderID);
    }

    public void addNewItemToOrder(Item item, UUID orderID) {
        ClientOrder referenceById = orderRepository.getReferenceById(orderID);
        item.setClientOrder(referenceById);
        itemRepository.save(item);
        event.sendMessage(String.valueOf(referenceById));
    }

}
