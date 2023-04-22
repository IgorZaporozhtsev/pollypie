package com.zeecoder.kitchen.webclient;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/json/v1/1/search.php")
public interface TheCocktailDbClient {
    @GetExchange
    String getCocktail(@RequestParam String s);
}
