package com.zeecoder.domains;

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
    @Size(min = 3, max = 10)
    private String firstName;
    @Size(min = 3, max = 10)
    private String lastName;
    @Digits(integer = 13, fraction = 0)
    @NotNull
    private String phoneNumber;
    @NotNull
    @Size(min = 5, max = 100)
    private String address;
    @Email
    @NotBlank
    private String email;
}
