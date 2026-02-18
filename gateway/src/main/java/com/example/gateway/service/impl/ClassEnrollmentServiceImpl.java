package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.gateway.service.ClassEnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassEnrollmentServiceImpl implements ClassEnrollmentService {
    AcademicClient academicClient;

    @Override
    public void enroll(EnrollStudentRequest request) {
        academicClient.createClassEnrollment(request);
    }
}
