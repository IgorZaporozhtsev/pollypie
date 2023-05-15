package com.zeecoder.recipient.service;

import com.zeecoder.common.exceptions.ApiRequestException;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.MenuRequest;
import com.zeecoder.recipient.dto.OrderDetailsResponse;
import com.zeecoder.recipient.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final DtoMapper dtoMapper;

    @Transactional
    public MenuDetailsResponse save(MenuRequest menuRequest) {
        RequestValidator.validateRequest(menuRequest);
        var savedOrder = orderRepository.save(dtoMapper.toOrder(menuRequest));
        log.info("Order {} was saved to database", savedOrder.getId().toString());
        return dtoMapper.toResponseDto(savedOrder);
    }

    public MenuDetailsResponse getById(UUID orderId) {
        var derivedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException(orderId.toString(), "GEEX001"));
        return dtoMapper.toResponseDto(derivedOrder);
    }

    public Page<MenuDetailsResponse> getOrders(Pageable page) {
        return orderRepository.findAll(page).map(dtoMapper::toResponseDto);
    }

    public List<MenuDetailsResponse> getOrdersDto() {
        return orderRepository.findAllOrderDTOs();
    }

    public List<OrderDetailsResponse> getOrdersWithItemsCount() {
        return orderRepository.findAllWithItemCount();
    }

    public void delete(UUID orderID) {
        orderRepository.deleteById(orderID);
    }

    public Optional<Order> findOpenedOrder(String name) {
        return orderRepository.findOpenedOrderByDate(name);
    }
}
