package com.zeecoder.kitchen;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.ItemRepository;
import com.zeecoder.domains.OrderRepository;
import com.zeecoder.domains.OrderState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KitchenService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public void saveOrder(ClientOrder order) {
        orderRepository.save(order);
        log.info("Order was saved 🎉🎉🎉" + order);
    }


    public void updateOrder(ClientOrder clientOrder) {
        clientOrder.setState(OrderState.PREPARED);
        orderRepository.save(clientOrder);

    }
}
