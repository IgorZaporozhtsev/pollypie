package com.zeecoder.kitchen.dto;


import com.zeecoder.common.dto.WorkerState;

public record TriggerRequest(WorkerState state) {
}
