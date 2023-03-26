package com.zeecoder.recipient;

import com.zeecoder.recipient.security.config.users.User;
import com.zeecoder.recipient.security.config.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.zeecoder.recipient.security.config.users.Role.*;

@SpringBootApplication(
        scanBasePackages = {
                "com.zeecoder.kafka",
                "com.zeecoder.domains",
                "com.zeecoder.recipient"
        }
)

@EntityScan(basePackages = {"com.zeecoder.domains", "com.zeecoder.recipient"})
@EnableJpaRepositories(basePackages = {"com.zeecoder.domains", "com.zeecoder.recipient"})
public class RecipientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipientApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = User.builder()
                    .username("admin")
                    .lastName("Nicolson")
                    .password(passwordEncoder.encode("password"))
                    .authorities(ADMIN.getAuthorities())
                    .build();

            User user = User.builder()
                    .username("user")
                    .lastName("Pitter")
                    .password(passwordEncoder.encode("password"))
                    //authorities holds together roles - ROLE_ and authorities READ. Define roles by prefix ROLE_
                    .authorities(USER.getAuthorities())
                    .build();


            User moderator = User.builder()
                    .username("moderator")
                    .lastName("Geremy")
                    .password(passwordEncoder.encode("password"))
                    .authorities(MODERATOR.getAuthorities())
                    .build();

            User client = User.builder()
                    .username("client")
                    .lastName("Lizerman")
                    .password(passwordEncoder.encode("password"))
                    .authorities(CLIENT.getAuthorities())
                    .build();


            //org.springframework.security.core.userdetails.User.builder().roles()

            userRepository.saveAll(List.of(user, admin, moderator, client));


        };
    }

}
