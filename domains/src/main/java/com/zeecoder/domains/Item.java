package com.zeecoder.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
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
    UUID itemID;
    @Column(name = "name")
    String name;

    @Column(name = "amount")
    Integer amount;

    //TODO change to FetchType.LAZY
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_clientOrder")
    ClientOrder clientOrder;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true)

    List<Addition> adds = new ArrayList<>();

    @JsonBackReference
    public ClientOrder getClientOrder() {
        return clientOrder;
    }
}
