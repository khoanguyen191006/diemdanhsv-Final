package com.example.gateway.controller.external;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.gateway.service.ClassEnrollmentService;
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
public class ClassEnrollmentExternalController {
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
