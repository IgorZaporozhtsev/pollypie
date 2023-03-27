package com.zeecoder.recipient;

import com.zeecoder.recipient.security.repo.UserRepository;
import com.zeecoder.recipient.security.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.zeecoder.recipient.security.user.Role.*;

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
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User admin = adminUser(passwordEncoder);
            User client = clientUser(passwordEncoder);
            userRepository.saveAll(List.of(admin, client));
        };
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
