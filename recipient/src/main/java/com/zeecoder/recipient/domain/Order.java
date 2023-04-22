package com.zeecoder.recipient.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 9047326882838679982L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotBlank
    String title;

    BigDecimal totalPrice;

    LocalDateTime orderDate = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id_fk")
    private List<Item> items = new ArrayList<>();

    @Embedded
    @Valid
    ContactDetails contactDetails;

    public void addOrderItems(List<Item> items) {
        this.getItems().clear();
        this.getItems().addAll(items);
    }
}

