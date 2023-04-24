package com.zeecoder.recipient.repository;

import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT c FROM Order c WHERE c.orderDate = (SELECT MIN(e.orderDate) FROM Order e WHERE e.status = 'OPEN')")
    Optional<Order> findOpenedOrderByDate(String orderState);

    @Query("SELECT im FROM Item im JOIN FETCH im.product ad WHERE im.id = :orderId")
    List<Item> findOrderRelatedChildren(UUID orderId);
}
