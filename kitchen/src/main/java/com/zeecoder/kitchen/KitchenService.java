package com.zeecoder.kitchen;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.ItemRepository;
import com.zeecoder.domains.OrderRepository;
import com.zeecoder.domains.OrderState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class KitchenService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;


    public void apply(String data) {
        //serialise data
        updateOrder(UUID.randomUUID());

        //sent to delivery Topic
    }

    public void updateOrder(UUID orderID) {
        //retrieve data from DataBase and update state
        ClientOrder derivedOrder = orderRepository.findById(orderID)
                .orElseThrow(() -> new IllegalArgumentException("There is no Order with: " + orderID));

        derivedOrder.setState(OrderState.PREPARED);

    }
}
