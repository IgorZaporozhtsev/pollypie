package com.zeecoder.kitchen.repository;

import com.zeecoder.kitchen.domain.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<ClientOrder, UUID> {
}
