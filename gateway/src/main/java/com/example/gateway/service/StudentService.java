package com.example.gateway.service;

import com.example.gateway.dto.request.student.StudentImageUploadRequest;
import com.example.gateway.dto.request.student.StudentUploadRequest;
import com.example.gateway.dto.response.student.StudentInfoResponse;

public interface StudentService {
    void createStudent(StudentUploadRequest request);

    StudentInfoResponse verifyFace (StudentImageUploadRequest request);
}

