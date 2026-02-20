package com.example.academicService.service;

import com.example.academicService.dto.request.student.StudentUploadRequest;
import com.example.academicService.dto.response.student.StudentResponse;

public interface StudentService {
    String createStudent(StudentUploadRequest request);

    StudentResponse getStudentByDecodeStudentId(String decodeStudentId);
}
