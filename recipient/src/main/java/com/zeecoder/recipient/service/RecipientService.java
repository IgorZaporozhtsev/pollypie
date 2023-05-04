package com.zeecoder.recipient.service;

import com.zeecoder.common.dto.OrderEvent;
import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.common.exceptions.ApiRequestException;
import com.zeecoder.kafka.Producer;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.OrderStatus;
import com.zeecoder.recipient.dto.Menu;
import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.MenuRequest;
import com.zeecoder.recipient.dto.OrderDetailsResponse;
import com.zeecoder.recipient.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.zeecoder.common.dto.WorkerState.FREE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipientService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Producer<OrderEvent> producer;
    private WorkerState workerState;

    @Transactional
    public MenuDetailsResponse save(MenuRequest menuRequest) {
        validateRequest(menuRequest);

        var definitions = menuRequest.getMenuList().stream()
                .collect(Collectors.toMap(Menu::getDish, Menu::getCount));

        var order = Order.builder()
                .title(menuRequest.getTitle())
                .orderDefinitions(definitions)
                .contactDetails(menuRequest.getContactDetails())
                .build();
        var savedOrder = orderRepository.save(order);
        log.info("Order {} was saved to database", order.getId().toString());
        return toResponseDto(savedOrder);
    }

    private void validateRequest(MenuRequest menuRequest) {
        var hasDuplicates = menuRequest.getMenuList().stream()
                .map(Menu::getDish)
                .distinct()
                .count() != menuRequest.getMenuList().size();

        if (hasDuplicates) {
            throw new ApiRequestException("dish in menu list has duplicates", "GEEX003");
        }
    }

    public MenuDetailsResponse getById(UUID orderId) {
        var derivedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException(orderId.toString(), "GEEX001"));
        return toResponseDto(derivedOrder);
    }

    public Page<MenuDetailsResponse> getOrders(Pageable page) {
        return orderRepository.findAll(page).map(this::toResponseDto);
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

    public void provideNextOrder(WorkerState workerState) {
        this.workerState = workerState;
    }

    @Transactional
    @Scheduled(fixedDelay = 20_000)
    public void processNextOrder() {
        log.info("Scheduler processNextOrder is running....");
        if (FREE.equals(workerState)) {
            log.info("Starting to seek an order....");
            orderRepository.findOpenedOrderByDate(OrderStatus.OPEN.name())
                    .ifPresentOrElse(this::process,
                            () -> log.info("Oops, perhaps you don't have orders anymore"));
        }
    }

    public void process(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);

        var orderPadDto = OrderPadDto.builder()
                .orderId(order.getId())
                .wishes(List.of("Margarita"))
                .build();

        producer.sendMessage("recipient", order.getId(), assembleOrderEvent(orderPadDto));
    }

    private OrderEvent assembleOrderEvent(OrderPadDto orderPadDto) {
        return new OrderEvent("OPEN", "order was opened at" + LocalDate.now(), orderPadDto);
    }

    public MenuDetailsResponse toResponseDto(Order order) {
        return modelMapper.map(order, MenuDetailsResponse.class);
    }
}
