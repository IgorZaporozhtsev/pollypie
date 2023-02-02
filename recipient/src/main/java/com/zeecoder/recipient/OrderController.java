package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.Item;
import com.zeecoder.recipient.dto.SimpleOrder;
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
    private final SimpleOrderDTOMapper simpleOrderDTOMapper;

    @GetMapping(value = "{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientOrder get(@PathVariable("orderID") UUID orderID) {
        return service.get(orderID)
                .orElseThrow(() -> new IllegalArgumentException("There is no"));
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

    @PostMapping("{orderID}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void newItem(@RequestBody Item item, @PathVariable("orderID") UUID orderID) {
        service.addNewItemToOrder(item, orderID);
    }

    @DeleteMapping("{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("orderID") UUID orderID) {
        service.delete(orderID);
    }

    @GetMapping(value = "/dto/{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public SimpleOrder getOrderDTO(@PathVariable("orderID") UUID orderID) {


        return service.get(orderID)
                .map(simpleOrderDTOMapper::apply)
                .orElseThrow();
    }
}

