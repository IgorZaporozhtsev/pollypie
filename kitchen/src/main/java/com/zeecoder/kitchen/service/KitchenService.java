package com.zeecoder.kitchen.service;

import com.zeecoder.common.dto.OrderPadDto;
import com.zeecoder.common.dto.WorkerState;
import com.zeecoder.kafka.Producer;
import com.zeecoder.kitchen.dto.TriggerRequest;
import com.zeecoder.kitchen.webclient.TheCocktailDbClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.zeecoder.common.dto.WorkerState.BUSY;
import static com.zeecoder.common.dto.WorkerState.FREE;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenService {

    private final Producer<WorkerState> event;
    private final TheCocktailDbClient cocktailDbClient;

    @Transactional
    public void createItem(OrderPadDto itemDto) throws InterruptedException {
        executeKitchenProcess(new TriggerRequest(BUSY)); //should make delay for Scheduler

        String cocktail = findCocktail("Margarita");
        doShake(cocktail);

        TimeUnit.SECONDS.sleep(10);

        executeKitchenProcess(new TriggerRequest(FREE));
    }

    private String findCocktail(String name) {
        return cocktailDbClient.getCocktail(name);
    }

    private void doShake(String cocktail) {

    }

    public void executeKitchenProcess(TriggerRequest request) {
        event.sendMessage("kitchen", request.state());
        log.info("Kitchen status is {}", request.state());
    }
}
