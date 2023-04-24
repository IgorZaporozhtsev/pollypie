package com.zeecoder.recipient.controller;

import com.zeecoder.common.exceptions.ApiRequestException;
import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.dto.OrderDtoMapper;
import com.zeecoder.recipient.dto.OrderResponse;
import com.zeecoder.recipient.service.RecipientService;
import com.zeecoder.recipient.util.SimpleOrderDTOMapper;
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
@RequestMapping("/api/v1/client-order")
//TODO: replace Entity to Dto, use ModelMapper
public class RecipientController {

    private final RecipientService service;
    private final SimpleOrderDTOMapper simpleOrderDTOMapper;

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Order getOrder(@PathVariable("id") UUID id) {
        return service.getById(id)
                .orElseThrow(() -> new ApiRequestException(id.toString(), "GEEX001"));
    }

    @GetMapping(value = "/dto/{orderID}")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse getOrderDTO(@PathVariable("orderID") UUID orderID) {
        return service.getById(orderID)
                .map(simpleOrderDTOMapper)
                .orElseThrow();
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Order> getAll(
            @PageableDefault(sort = "id", size = 5) Pageable page) {
        return service.getOrders(page);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse saveOrder(@Valid @RequestBody Order order) {
        var saved = service.save(order);
        return new OrderResponse(saved.getId(), saved.getContactDetails());

    }

    @PostMapping("{orderID}")
    @ResponseStatus(code = HttpStatus.CREATED)
    //TODO return ItemResponse
    public void newItem(@RequestBody Item item, @PathVariable("orderID") UUID orderID) {
        service.addNewItemToOrder(item, orderID);
    }

    @DeleteMapping("{orderID}")
    @PreAuthorize(value = "hasAuthority('internal:delete')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("orderID") UUID orderID) {
        service.delete(orderID);
    }


    @PostMapping("/test")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void testMapper(@RequestBody OrderDtoMapper dto) {
        service.saveTestDto(dto);
    }
}

