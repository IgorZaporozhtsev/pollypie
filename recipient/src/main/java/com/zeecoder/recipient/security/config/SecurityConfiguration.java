package com.zeecoder.recipient.security.config;

import com.zeecoder.recipient.security.config.users.Role;
import com.zeecoder.recipient.security.config.users.UserPermission;
import com.zeecoder.recipient.security.config.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //enable @PreAuthorize
@AllArgsConstructor
public class SecurityConfiguration {

    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String createOrderEndpoint = "/api/v1/client-order/**";

        //FYI order doest really matter when we configure restriction to endpoints
        http.
                csrf().disable().
                authorizeHttpRequests()
                .requestMatchers("/").permitAll()

                //Http method can be configured by enable @EnableMethodSecurity, and
                //put restriction in Controller using  @PreAuthorize("hasRole('ADMIN')") and @PreAuthorize("hasAuthority('ADMIN')")

                .requestMatchers(GET, createOrderEndpoint).hasAuthority(UserPermission.READ_EXTERNAL.getPermission())
                .requestMatchers(POST, createOrderEndpoint).hasAuthority(UserPermission.WRITE_INTERNAL.getPermission())
                .requestMatchers(DELETE, createOrderEndpoint).hasAnyRole(Role.ADMIN.name(), Role.MODERATOR.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return firstName -> userRepository.findByUsername(firstName)
                .orElseThrow(() -> new EntityNotFoundException("There is no user"));

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
