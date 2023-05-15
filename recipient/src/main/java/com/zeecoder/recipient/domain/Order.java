package com.zeecoder.recipient.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    @Builder.Default
    LocalDateTime orderDate = LocalDateTime.now();

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    OrderStatus status = OrderStatus.OPEN;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "item_name")
    @Column(name = "quantity")
    @CollectionTable(name = "order_definitions", joinColumns = @JoinColumn(name = "id"))
    Map<String, Integer> orderDefinitions = new HashMap<>();

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Item> items = new ArrayList<>();

    @Embedded
    @Valid
    @NotNull
    ContactDetails contactDetails;

    public void addOrderItems(List<Item> items) {
        this.getItems().clear();
        this.getItems().addAll(items);
    }
}

