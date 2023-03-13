package com.zeecoder.recipient;

import com.zeecoder.domains.*;
import com.zeecoder.kafka.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipientServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderEvent<ClientOrder> event;

    @InjectMocks
    private RecipientService recipientService;

    private ClientOrder order;

    @BeforeEach
    void setUp() {
        order = ClientOrder.builder()
                .orderID(UUID.randomUUID())
                .state(OrderState.OPEN)
                .build();
    }

    @Test
    void testSave() {
        recipientService.save(order);
        verify(event, times(1)).sendMessage(order);
    }

    @Test
    void testGet() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<ClientOrder> retrievedOrder = recipientService.get(orderId);

        assertThat(retrievedOrder).isPresent().contains(order);
    }

    @Test
    void testGetOrders() {
        Page<ClientOrder> page = Page.empty();
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ClientOrder> retrievedPage = recipientService.getOrders(Pageable.unpaged());

        assertThat(retrievedPage).isEqualTo(page);
    }

    @Test
    void testDelete() {
        UUID orderId = UUID.randomUUID();
        doNothing().when(orderRepository).deleteById(orderId);

        recipientService.delete(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testAddNewItemToOrder_whenOrderIsOpen() {
        Item item = new Item();
        UUID orderId = UUID.randomUUID();
        when(orderRepository.getReferenceById(orderId)).thenReturn(order);

        recipientService.addNewItemToOrder(item, orderId);

        assertThat(item.getClientOrder()).isEqualTo(order);
        verify(itemRepository, times(1)).save(item);
        verify(event, times(1)).sendMessage(order);
    }

    @Test
    void testAddNewItemToOrder_whenOrderIsNotOpen() {
        Item item = new Item();
        order.setState(OrderState.FINISHED);
        UUID orderId = UUID.randomUUID();
        when(orderRepository.getReferenceById(orderId)).thenReturn(order);

        recipientService.addNewItemToOrder(item, orderId);

        assertThat(item.getClientOrder()).isNull();
        verify(itemRepository, times(0)).save(item);
        verify(event, times(0)).sendMessage(order);
    }
}
