package com.zeecoder.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic deliveryTopic() {
        return TopicBuilder.name("delivery")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic kitchenTopic() {
        return TopicBuilder.name("kitchen")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic recipientTopic() {
        return TopicBuilder.name("recipient")
                .partitions(2)
                .replicas(1)
                .build();
    }
}
