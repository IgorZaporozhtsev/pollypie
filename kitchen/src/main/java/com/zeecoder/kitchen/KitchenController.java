package com.zeecoder.kitchen;

import com.zeecoder.common.ClientOrder;
import com.zeecoder.common.OrderRepository;
import com.zeecoder.common.OrderState;
import com.zeecoder.common.exceptions.ApiRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/kitchen")
@AllArgsConstructor
public class KitchenController {

    private final OrderRepository orderRepository;

    @GetMapping(value = "{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public String checkOrderStatus(@PathVariable("orderID") UUID orderID) {
        log.info("Checkin kitchen available status");

        return orderRepository.findById(orderID)
                .map(ClientOrder::getState)
                .map(OrderState::toString)
                .orElseThrow(() -> new ApiRequestException(orderID.toString(), "GEEX001"));

    }
    //https://localhost:8081/api/v1/kitchen/e2923664-f738-4166-acbb-b1fe47ebb484
}
