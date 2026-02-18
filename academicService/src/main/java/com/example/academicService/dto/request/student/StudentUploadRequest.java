package com.example.academicService.dto.request.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUploadRequest {
    String studentCode;
    String fullName;
    String email;
    String status;
}
