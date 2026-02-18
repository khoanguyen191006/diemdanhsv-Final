package com.example.gateway.service;

import com.example.gateway.dto.request.enrollStudent.EnrollStudentRequest;

public interface ClassEnrollmentService {
    void enroll(EnrollStudentRequest request);
}
