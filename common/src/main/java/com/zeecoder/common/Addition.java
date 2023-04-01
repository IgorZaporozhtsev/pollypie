package com.zeecoder.common;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(of = {"addID", "name"})
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
    UUID addID;

    @Column(name = "name")
    String name;

    BigDecimal itemPrice;
}
