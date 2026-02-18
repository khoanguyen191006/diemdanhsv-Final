package com.example.academicService.dto.request.enrollStudent;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollStudentRequest {
    String classId;
    String studentId;
}
