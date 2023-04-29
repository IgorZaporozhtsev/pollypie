package com.zeecoder.recipient.controller;

import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.MenuRequest;
import com.zeecoder.recipient.dto.OrderDetailsResponse;
import com.zeecoder.recipient.service.RecipientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client-order")
public class RecipientController {

    private final RecipientService service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public MenuDetailsResponse saveOrder(@Valid @RequestBody MenuRequest menuRequest) {
        return service.save(menuRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MenuDetailsResponse getOrder(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Page<MenuDetailsResponse> getAll(
            @PageableDefault(sort = "id", size = 5) Pageable page) {
        return service.getOrders(page);
    }

    @GetMapping("/dtos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<MenuDetailsResponse> getAllDTOs() {
        return service.getOrdersDto();
    }

    @GetMapping("/items-count")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrderDetailsResponse> getAllOrdersItemCount() {
        return service.getOrdersWithItemsCount();
    }

    @DeleteMapping("{orderID}")
    @PreAuthorize(value = "hasAuthority('internal:delete')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("orderID") UUID orderID) {
        service.delete(orderID);
    }
}

