package com.zeecoder.recipient.repository;

import com.zeecoder.recipient.domain.Item;
import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.OrderDetailsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 1.Do one select query instead findAll that do a lot of select query.
     */
    @Query("SELECT new com.zeecoder.recipient.dto.MenuDetailsResponse(o.id, o.contactDetails) from Order o")
    List<MenuDetailsResponse> findAllOrderDTOs();

    /**
     * 2. Instead of this Query we can do List<MenuResponse> findAllBy();
     * and Spring Date generate same query under hood and map to dto
     */
    List<MenuDetailsResponse> findAllBy();

    /**
     * 3. We can use generics and work with any DTOs 😀
     */
    <T> Page<T> findAllBy(Class<T> type, Pageable pageable);

    /**
     * 4. Combination of queries
     */
    @Query("SELECT new com.zeecoder.recipient.dto.OrderDetailsResponse(o.id, size(o.items) ) FROM Order o")
    List<OrderDetailsResponse> findAllWithItemCount();


}
