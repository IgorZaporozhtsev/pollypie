package com.zeecoder.recipient.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetails {
    @NotNull
    @Size(min = 2, max = 10)
    private String firstName;
    @Size(min = 2, max = 10)
    private String lastName;
    @Pattern(regexp = "[\\d\\s()+-]+")
    @NotNull
    private String phoneNumber;
    @NotNull
    @Size(min = 5, max = 100)
    private String address;
    @Email
    @NotBlank
    private String email;
}
