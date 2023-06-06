package com.zeecoder.kitchen.webclient.config;

import com.zeecoder.kitchen.webclient.DataRecordsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
public class HttpCallConfiguration {

    @Bean
    public DataRecordsClient dataRecordsClient() {
        WebClient client = WebClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError, resp ->
                        Mono.just(new ExternalServiceException("External service unreachable")))
                .baseUrl("https://www.thecocktaildb.com")
                .build();

        WebClientAdapter clientAdapter = WebClientAdapter.forClient(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(DataRecordsClient.class);
    }
}
