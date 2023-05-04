package com.zeecoder.recipient.service;

import com.zeecoder.common.exceptions.ApiRequestException;
import com.zeecoder.recipient.dto.Menu;
import com.zeecoder.recipient.dto.MenuRequest;

public class RequestValidator {

    private RequestValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateRequest(MenuRequest menuRequest) {
        var hasDuplicates = menuRequest.getMenuList().stream()
                .map(Menu::getDish)
                .distinct()
                .count() != menuRequest.getMenuList().size();

        if (hasDuplicates) {
            throw new ApiRequestException("dish in menu list has duplicates", "GEEX003");
        }
    }

}
