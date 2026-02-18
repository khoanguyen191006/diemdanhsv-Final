package com.example.academicService.service;

import com.example.academicService.dto.request.enrollStudent.EnrollStudentRequest;

public interface ClassEnrollmentService {
    void enroll(EnrollStudentRequest request);
}
