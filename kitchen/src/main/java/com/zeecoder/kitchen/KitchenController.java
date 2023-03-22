package com.zeecoder.kitchen;

import com.zeecoder.kitchen.async.AsyncProcessService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("api/v1/kitchen")
@AllArgsConstructor
public class KitchenController {

    private final AsyncProcessService asyncProcessService;

    @GetMapping(value = "{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<String> checkOrderStatus(@PathVariable("orderID") UUID orderID) {
        log.info("Start checkin kitchen available status");
        return asyncProcessService.process(orderID).thenApply(result -> {
            log.info("Return Order state to recipient service");
            return result;
        });
    }
}
