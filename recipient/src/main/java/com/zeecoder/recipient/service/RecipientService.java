package com.zeecoder.recipient.service;

import com.zeecoder.common.dto.OrderEvent;
import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.kafka.Producer;
import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.OrderStatus;
import com.zeecoder.recipient.dto.OrderDtoMapper;
import com.zeecoder.recipient.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class RecipientService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final Producer<OrderEvent> producer;

    @Transactional
    public Order save(Order order) {
        Order saved = orderRepository.save(order);
        log.info(String.format("Order %s was saved to database", order.getId().toString()));
        return saved;
    }

    public Optional<Order> getById(UUID orderId) {
        return orderRepository.findById(orderId);
    }

    public Page<Order> getOrders(Pageable page) {
        return orderRepository.findAll(page);
    }

    public void delete(UUID orderID) {
        orderRepository.deleteById(orderID);
    }

    @Transactional
    public void addNewItemToOrder(Item item, UUID orderID) {
        //TODO add if order has status OPEN
        Order derivedOrder = orderRepository.getReferenceById(orderID);
        if (derivedOrder.getStatus().equals(OrderStatus.OPEN)) {
            var items = derivedOrder.getItems();
            items.add(item);
            derivedOrder.addOrderItems(items);
            orderRepository.save(derivedOrder);
        }
    }

    public void provideNextOrder() {
        log.info("Status is FREE. Start to providing order....");
        orderRepository.findFirstByOrderByOrderDateDesc()
                .ifPresentOrElse(this::process,
                        () -> log.info("Oops, perhaps you don't have orders anymore"));
    }

    private void process(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        var saved = orderRepository.save(order);

        //order.setStatus(OrderStatus.IN_PROGRESS);

        //TODO Entity haven't saved need
        // cover with test, helped cascade = CascadeType.PERSIST instead cascade = CascadeType.ALL

        var orderPadDto = OrderPadDto.builder()
                .orderId(saved.getId())
                .wishes(List.of("Margarita"))
                .build();

        producer.sendMessage("recipient", assembleOrderEvent(orderPadDto));
        //TODO change status only we get acknowledgment form kafka that Order IN_PROGRESS
    }

    private OrderEvent assembleOrderEvent(OrderPadDto orderPadDto) {
        return new OrderEvent("OPEN", "order was opened at" + LocalDate.now(), orderPadDto);
    }

    //TODO provide all with dto classes
    public void saveTestDto(OrderDtoMapper dto) {
        var order = modelMapper.map(dto, Order.class);
        log.info("order " + order);
    }
}
