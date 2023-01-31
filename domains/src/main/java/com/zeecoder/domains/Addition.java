package com.zeecoder.domains;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(of = {"itemID", "name"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Addition implements Serializable {

    @Serial
    private static final long serialVersionUID = 3675054516024894342L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID itemID;

    @Column(name = "name")
    String name;
}
