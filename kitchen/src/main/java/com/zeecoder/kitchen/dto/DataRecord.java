package com.zeecoder.kitchen.dto;


import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID drId;
    private String name;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode node;
}
