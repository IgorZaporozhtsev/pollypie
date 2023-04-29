package com.zeecoder.recipient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Menu {
    @NotBlank String dish;
    @NotNull Integer count;
}
