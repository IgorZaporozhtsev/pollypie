package com.zeecoder.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client-order")
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderID;

    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        return "ClientOrder{" +
                "orderID=" + orderID +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}

