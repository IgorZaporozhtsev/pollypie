package com.zeecoder.kitchen.webclient.config;

import com.zeecoder.kitchen.webclient.TheCocktailDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpCallConfiguration {

    @Bean
    public TheCocktailDbClient kitchenClient() {
        WebClient client = WebClient.builder().baseUrl("https://www.thecocktaildb.com").build();
        WebClientAdapter clientAdapter = WebClientAdapter.forClient(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(TheCocktailDbClient.class);
    }
}
