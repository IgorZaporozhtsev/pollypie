package com.zeecoder.kitchen.controller;

import com.zeecoder.kitchen.dto.TriggerRequest;
import com.zeecoder.kitchen.service.KitchenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/kitchen")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void triggerFreeEntryPoint(@RequestBody TriggerRequest triggerFreeRequest) {
        kitchenService.executeKitchenProcess(triggerFreeRequest);
    }
}
