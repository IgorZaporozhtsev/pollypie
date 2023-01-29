package com.zeecoder.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Addition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID itemID;
    @Column(name = "name")
    String name;


}
