package com.zeecoder.recipient.util;

import com.zeecoder.recipient.domain.Order;
import com.zeecoder.recipient.domain.OrderStatus;
import com.zeecoder.recipient.repository.OrderRepository;
import com.zeecoder.recipient.security.repo.UserRepository;
import com.zeecoder.recipient.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static com.zeecoder.recipient.security.user.Role.*;

@Component
@RequiredArgsConstructor
public class EntityGenerator {
    private final PodamFactory podamFactory;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void generate() {
        //changeData();
        User admin = adminUser(passwordEncoder);
        User client = clientUser(passwordEncoder);
        userRepository.saveAll(List.of(admin, client));

        //generateDomainEntities();
    }

    private void changeData() {
        List<Order> all = orderRepository.findAll();
        all.forEach(clientOrder -> {
                    OrderStatus value = OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)];
                    clientOrder.setStatus(value);
                    orderRepository.save(clientOrder);
                }

        );

    }

    private void generateDomainEntities() {
        podamFactory.manufacturePojo(Order.class);

        IntStream.rangeClosed(0, 1).forEach(
                i -> {
                    Order order = podamFactory.manufacturePojo(Order.class);
                    orderRepository.save(order);
                }
        );
    }

    private static User clientUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username("client")
                .lastName("Lizerman")
                .email("lizerman@gmail.com")
                .password(passwordEncoder.encode("Lizerman"))
                .authorities(CLIENT.getAuthorities())
                .build();
    }

    private static User getModerator(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username("moderator")
                .lastName("Geremy")
                .password(passwordEncoder.encode("Geremy"))
                .authorities(MODERATOR.getAuthorities())
                .build();
    }

    private static User internalUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username("user")
                .lastName("Pitter")
                .password(passwordEncoder.encode("Pitter"))
                //authorities holds together roles - ROLE_ and authorities READ. Define roles by prefix ROLE_
                .authorities(USER.getAuthorities())
                .build();
    }

    private static User adminUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username("admin")
                .lastName("Dickson")
                .email("dickson@gmail.com")
                .password(passwordEncoder.encode("Dickson"))
                .authorities(ADMIN.getAuthorities())
                .build();
    }
}
