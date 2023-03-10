package com.zeecoder.kitchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.zeecoder.kitchen",
                "com.zeecoder.kafka"
        }
)
@EntityScan("com.zeecoder.domains")
@EnableJpaRepositories("com.zeecoder.domains")
public class KitchenApplication {
    public static void main(String[] args) {
        SpringApplication.run(KitchenApplication.class, args);
    }

  /*  @Bean
    CommandLineRunner run(KitchenService service) {
        return args -> IntStream.rangeClosed(1, 100).forEach(i -> {

            ClientOrder order = new ClientOrder();

            var adds = List.of(
                    Addition.builder()
                            .name("Ketchup" + i)
                            .itemID(UUID.randomUUID()).build()
            );

            var items = List.of(
                    Item.builder()
                            .itemID(UUID.randomUUID())
                            .name("Pizza" + i)
                            .clientOrder(order)
                            .adds(adds)
                            .build()
            );

            order.setOrderID(UUID.randomUUID());
            order.setDescription("generated order, number " + i);
            order.setItems(items);
            order.setState(OrderState.OPEN);

            service.saveOrder(order);

        });
    }*/
}
