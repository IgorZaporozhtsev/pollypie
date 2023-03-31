package com.zeecoder.recipient;

import com.zeecoder.recipient.util.EntityGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

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

    @Bean
    CommandLineRunner run(EntityGenerator entityGenerator) {
        return args -> entityGenerator.generate();
    }

    @Bean
    PodamFactory podamFactory() {
        return new PodamFactoryImpl();
    }
}
