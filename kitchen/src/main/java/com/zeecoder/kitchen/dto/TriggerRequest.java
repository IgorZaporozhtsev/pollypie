package com.zeecoder.kitchen.dto;


import com.zeecoder.common.WorkerState;

public record TriggerRequest(WorkerState state) {
}
