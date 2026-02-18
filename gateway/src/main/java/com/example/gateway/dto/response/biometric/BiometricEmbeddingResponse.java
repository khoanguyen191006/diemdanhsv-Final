package com.example.gateway.dto.response.biometric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BiometricEmbeddingResponse {

    @JsonProperty("_id")
    String id;

    @JsonProperty("student_id_hash")
    String studentIdHash;

    List<List<Double>> embeddings;

    @JsonProperty("model_version")
    String modelVersion;

    @JsonProperty("created_at")
    Instant createdAt;
}