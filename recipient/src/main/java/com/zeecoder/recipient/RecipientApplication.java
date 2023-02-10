package com.zeecoder.recipient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.zeecoder.kafka",
                "com.zeecoder.domains",
                "com.zeecoder.recipient"
        }
)
@EntityScan("com.zeecoder.domains")
@EnableJpaRepositories("com.zeecoder.domains")
public class RecipientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipientApplication.class, args);
    }
}
