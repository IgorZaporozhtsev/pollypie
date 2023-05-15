package com.zeecoder.recipient.dto;

import com.zeecoder.recipient.domain.ContactDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class MenuRequest {
    @NotNull List<@Valid Menu> menuList;
    @NotNull @Valid ContactDetails contactDetails;
    @NotBlank String title;

}
