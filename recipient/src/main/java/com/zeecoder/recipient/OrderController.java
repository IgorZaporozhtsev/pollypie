package com.zeecoder.recipient;

import com.zeecoder.domains.ClientOrder;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/client-order")
public class OrderController {

    private final OrderRepo repo;

    @PostMapping
    public void saveOrder(@RequestBody ClientOrder order) {
        repo.save(order);
    }
}
