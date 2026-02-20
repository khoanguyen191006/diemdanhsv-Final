package com.example.academicService.dto.response.student;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    String studentCode;
    String fullName;
    String email;
    String status;
}
