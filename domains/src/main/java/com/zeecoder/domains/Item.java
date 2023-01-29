package com.zeecoder.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID itemID;
    @Column(name = "name")
    String name;

    @Column(name = "amount")
    Integer amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Addition> adds = new ArrayList<>();

}
