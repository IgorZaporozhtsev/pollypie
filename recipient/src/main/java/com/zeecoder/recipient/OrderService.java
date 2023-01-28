package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    OrderRepository repository;

    public void save(ClientOrder order) {
        repository.save(order);
    }
}
