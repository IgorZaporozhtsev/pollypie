package com.zeecoder.recipient;

import com.zeecoder.domains.*;
import com.zeecoder.kafka.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class RecipientService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderEvent<ClientOrder> event;


    public void save(ClientOrder order) {
        event.sendMessage(order);
        log.info("Data from kafka is sent: 🎉🎉🎉" + order);
    }

    public Optional<ClientOrder> get(UUID orderID) {
        return orderRepository.findById(orderID);
    }

    public Page<ClientOrder> getOrders(Pageable page) {
        return orderRepository.findAll(page);
    }

    public void delete(UUID orderID) {
        orderRepository.deleteById(orderID);
    }

    public void addNewItemToOrder(Item item, UUID orderID) {
        //TODO add if order has status OPEN
        ClientOrder derivedOrder = orderRepository.getReferenceById(orderID);
        if (derivedOrder.getState().equals(OrderState.OPEN)) {
            item.setClientOrder(derivedOrder);
            itemRepository.save(item);
            event.sendMessage(derivedOrder);
        }
    }

}
