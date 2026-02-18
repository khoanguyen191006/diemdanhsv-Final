package com.example.academicService.service;

import com.example.academicService.dto.request.student.StudentUploadRequest;

public interface StudentService {
    String createStudent(StudentUploadRequest request);
}
