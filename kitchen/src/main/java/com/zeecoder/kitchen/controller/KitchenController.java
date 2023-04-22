package com.zeecoder.kitchen.controller;

import com.zeecoder.kitchen.dto.TriggerRequest;
import com.zeecoder.kitchen.service.KitchenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/kitchen")
@AllArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @PostMapping
    public void triggerFreeEntryPoint(@RequestBody TriggerRequest triggerFreeRequest) {
        kitchenService.executeKitchenProcess(triggerFreeRequest);
    }
}
