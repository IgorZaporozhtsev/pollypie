package com.zeecoder.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString(of = {"orderID", "description", "items"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_order")
public class ClientOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 9047326882838679982L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "order_id")
    UUID orderID;

    @NotBlank
    String description;

    @Enumerated(value = EnumType.STRING)
    OrderState state;

    @OneToMany(
            mappedBy = "clientOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    List<Item> items = new ArrayList<>();

    @JsonManagedReference
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        items.forEach(item -> item.setClientOrder(this));
    }

}

