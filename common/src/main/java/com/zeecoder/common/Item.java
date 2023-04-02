package com.zeecoder.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(of = {"itemID", "name", "amount", "adds"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = -3366734895072375458L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    UUID itemID;
    @Column(name = "name")
    String name;

    @NotBlank
    BigDecimal itemPrice;

    @Column(name = "amount")
    Integer amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_order_id")
    ClientOrder clientOrder;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_item_id")
    List<Addition> adds = new ArrayList<>();

    @JsonBackReference
    public ClientOrder getClientOrder() {
        return clientOrder;
    }
}
