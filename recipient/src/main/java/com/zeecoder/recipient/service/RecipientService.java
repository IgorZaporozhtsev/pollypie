package com.zeecoder.recipient.service;

import com.zeecoder.common.*;
import com.zeecoder.kafka.OrderEvent;
import com.zeecoder.recipient.http_client.KitchenClient;
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
    private final KitchenClient kitchenClient;


    public void save(ClientOrder order) {
        event.sendMessage(order);
        log.info("Order was send to kitchen service: 🎉🎉🎉" + order);
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

    //TODO need create Entity for Kitchen and check if Sous-chef available and assign Order to him
    public void checkKitchenStatus(UUID orderID) {
        String state = kitchenClient.get(orderID);
        log.info(String.join(", ", "received OrderState", state));
    }

}
