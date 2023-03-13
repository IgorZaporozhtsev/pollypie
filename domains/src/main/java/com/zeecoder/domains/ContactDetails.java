package com.zeecoder.domains;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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
    private String firstName;

    private String lastName;
    //TODO validate phone number
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
}
