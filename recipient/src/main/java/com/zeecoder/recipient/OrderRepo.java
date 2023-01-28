package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<ClientOrder, UUID> {
}
