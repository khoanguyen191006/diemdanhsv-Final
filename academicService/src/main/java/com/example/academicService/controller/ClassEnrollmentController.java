package com.example.academicService.controller;

import com.example.academicService.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.academicService.dto.request.student.StudentUploadRequest;
import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.service.ClassEnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classEnrollment")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ClassEnrollmentController {
     private final ClassEnrollmentService classEnrollmentService;

    @PostMapping
    ResponseAPI<String> createClassEnrollment(@RequestBody EnrollStudentRequest request) {
        classEnrollmentService.enroll(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }
}
