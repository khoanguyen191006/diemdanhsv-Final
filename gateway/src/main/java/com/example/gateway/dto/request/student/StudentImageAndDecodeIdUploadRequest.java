package com.example.gateway.dto.request.student;

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
    String studentId;
    MultipartFile image;
}
