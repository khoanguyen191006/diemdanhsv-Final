package com.example.gateway.dto.request.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentImageAndDecodeIdUploadRequest {
    @JsonProperty("student_id_hash")
    String studentIdHash;
    MultipartFile image;
}
