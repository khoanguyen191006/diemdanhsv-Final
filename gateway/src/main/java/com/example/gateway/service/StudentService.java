package com.example.gateway.service;

import com.example.gateway.dto.request.student.StudentUploadRequest;

public interface StudentService {
    void createStudent(StudentUploadRequest request);
}

