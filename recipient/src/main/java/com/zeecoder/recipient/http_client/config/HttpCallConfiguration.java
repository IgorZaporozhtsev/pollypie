package com.zeecoder.recipient.http_client.config;

import com.zeecoder.recipient.http_client.KitchenClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpCallConfiguration {

    @Bean
    public KitchenClient kitchenClient() {
        WebClient client = WebClient.builder().baseUrl("http://localhost:8081/api/v1/").build();
        WebClientAdapter clientAdapter = WebClientAdapter.forClient(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(KitchenClient.class);
    }
}
