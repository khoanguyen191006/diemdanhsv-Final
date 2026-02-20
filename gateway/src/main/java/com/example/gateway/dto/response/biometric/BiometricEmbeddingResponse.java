package com.example.gateway.dto.response.biometric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BiometricEmbeddingResponse {

    boolean found;

    @JsonProperty("student_id_hash")
    String studentIdHash;

    double confidence;

    @JsonProperty("model_version")
    String modelVersion;

}