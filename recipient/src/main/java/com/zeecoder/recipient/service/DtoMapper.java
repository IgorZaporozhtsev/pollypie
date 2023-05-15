package com.zeecoder.recipient.service;

import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.dto.Menu;
import com.zeecoder.recipient.dto.MenuDetailsResponse;
import com.zeecoder.recipient.dto.MenuRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DtoMapper {

    private final ModelMapper modelMapper;

    public MenuDetailsResponse toResponseDto(Order order) {
        return modelMapper.map(order, MenuDetailsResponse.class);
    }

    public Order toOrder(MenuRequest menuRequest) {
        var definitions = menuRequest.getMenuList().stream()
                .collect(Collectors.toMap(Menu::getDish, Menu::getCount));

        return Order.builder()
                .title(menuRequest.getTitle())
                .orderDefinitions(definitions)
                .contactDetails(menuRequest.getContactDetails())
                .build();
    }
}
