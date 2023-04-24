package com.zeecoder.recipient.util;

import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SimpleOrderDTOMapper implements Function<Order, OrderResponse> {

    @Override
    public OrderResponse apply(Order order) {
        return new OrderResponse(order.getId(), order.getContactDetails());
    }
}
