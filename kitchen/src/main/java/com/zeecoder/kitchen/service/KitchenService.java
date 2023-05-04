package com.zeecoder.kitchen.service;

import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.kafka.Producer;
import com.zeecoder.kitchen.dto.TriggerRequest;
import com.zeecoder.kitchen.webclient.TheCocktailDbClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.zeecoder.common.dto.WorkerState.BUSY;
import static com.zeecoder.common.dto.WorkerState.FREE;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenService {

    private final Producer<WorkerState> event;
    private final TheCocktailDbClient cocktailDbClient;

    @Async
    public void createItem(OrderPadDto itemDto) throws InterruptedException {
        executeKitchenProcess(new TriggerRequest(BUSY)); //should make delay for Scheduler

        String cocktail = findCocktail("Margarita");
        doShake(cocktail);

        TimeUnit.MILLISECONDS.sleep(10_000); //so some work

        executeKitchenProcess(new TriggerRequest(FREE));
    }

    private String findCocktail(String name) {
        return cocktailDbClient.getCocktail(name);
    }

    private void doShake(String cocktail) {

    }

    public void executeKitchenProcess(TriggerRequest request) {
        log.info("Kitchen status is {}", request.state());
        event.sendMessage("kitchen", request.state());
    }
}
