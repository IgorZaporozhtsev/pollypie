package com.zeecoder.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Setter
    String name;

    @ElementCollection
    @CollectionTable(name = "INVENTORY", joinColumns = @JoinColumn(name = "STORE"))
    @Column(name = "COPIES_IN_STOCK")
    @MapKeyJoinColumn(name = "MOVIE", referencedColumnName = "ID")
    @Cascade(value = CascadeType.ALL)
    Map<Item, Integer> items = new HashMap<>();


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}

