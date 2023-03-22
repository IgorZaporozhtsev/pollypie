package com.zeecoder.recipient;

import com.zeecoder.common.ClientOrder;
import com.zeecoder.recipient.dto.SimpleOrder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SimpleOrderDTOMapper implements Function<ClientOrder, SimpleOrder> {

    @Override
    public SimpleOrder apply(ClientOrder clientOrder) {
        return new SimpleOrder(clientOrder.getOrderID(), clientOrder.getDescription());
    }
}
