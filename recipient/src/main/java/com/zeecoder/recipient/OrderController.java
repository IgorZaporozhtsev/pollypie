package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/client-order")
public class OrderController {

    private final OrderService service;

    @GetMapping(value = "{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientOrder get(@PathVariable("orderID") UUID orderID) {
        return service.get(orderID);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ClientOrder> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveOrder(@RequestBody ClientOrder order) {
        service.save(order);
    }
}
