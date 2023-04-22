package com.zeecoder.recipient.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = -3366734895072375458L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Valid
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
}
