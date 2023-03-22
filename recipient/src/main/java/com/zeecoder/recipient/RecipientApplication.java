package com.zeecoder.recipient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.zeecoder.kafka",
                "com.zeecoder.common",
                "com.zeecoder.recipient"
        }
)

@EntityScan(basePackages = {"com.zeecoder.common", "com.zeecoder.recipient"})
@EnableJpaRepositories(basePackages = {"com.zeecoder.common", "com.zeecoder.recipient"})
public class RecipientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipientApplication.class, args);
    }
}
