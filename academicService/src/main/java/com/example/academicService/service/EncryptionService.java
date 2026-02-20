package com.example.academicService.service;

public interface EncryptionService {
    String encodeStudentId(String studentId);

    String decodeStudentId(String encodedStudentId);
}
