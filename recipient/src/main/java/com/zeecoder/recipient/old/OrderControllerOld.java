/*
package com.zeecoder.recipient;

import com.zeecoder.domains.old.ClientOderDto;
import com.zeecoder.domains.old.ClientOrder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "{orderID}")
    public Optional<ClientOrder> getOrder(@PathVariable("orderID") UUID orderID){
        return Optional.of(orderService.get(orderID))
                .orElseThrow(()-> new IllegalArgumentException("There is no such order"));
    }


    @PostMapping
    public void saveOrder(@RequestBody ClientOderDto dto){
         orderService.save(dto);
    }
}
*/
