package com.zeecoder.recipient.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDtoMapper {
    public UUID orderId;
    public String description;
    public String state;
    public int age;
}
