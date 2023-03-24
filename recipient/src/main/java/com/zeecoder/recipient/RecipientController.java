package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import com.zeecoder.domains.Item;
import com.zeecoder.recipient.dto.SimpleOrder;
import com.zeecoder.recipient.exceptions.ApiRecipientException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/client-order")
public class RecipientController {

    private final RecipientService service;
    private final SimpleOrderDTOMapper simpleOrderDTOMapper;

    @GetMapping(value = "{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientOrder get(@PathVariable("orderID") UUID orderID) {
        return service.get(orderID)
                .orElseThrow(() -> new ApiRecipientException(orderID.toString(), "GEEX001"));
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ClientOrder> getAll(
            @PageableDefault(sort = "orderID", size = 5) Pageable page) {
        return service.getOrders(page);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    //TODO return id
    public void saveOrder(@Valid @RequestBody ClientOrder order) {
        service.save(order);
    }

    @PostMapping("{orderID}")
    @ResponseStatus(code = HttpStatus.CREATED)
    //TODO return id
    public void newItem(@RequestBody Item item, @PathVariable("orderID") UUID orderID) {
        service.addNewItemToOrder(item, orderID);
    }

    @DeleteMapping("{orderID}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
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

