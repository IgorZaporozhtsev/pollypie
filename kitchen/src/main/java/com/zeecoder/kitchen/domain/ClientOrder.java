package com.zeecoder.kitchen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderID;
    private String name;
    //@Enumerated
    private String status;

    private LocalDateTime createsAt;
    private LocalDateTime closedAt;



}
