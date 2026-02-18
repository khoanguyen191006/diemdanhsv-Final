package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.client.BiometricClient;
import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.student.StudentUploadRequest;
import com.example.gateway.exception.ApplicationException;
import com.example.gateway.exception.ErrorCode;
import com.example.gateway.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
    AcademicClient academicClient;
    BiometricClient biometricClient;

    @Override
    public void createStudent(StudentUploadRequest request) {
        ResponseAPI<String> studentId = academicClient.createStudent(request);
        if (studentId.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_CREATE_FAILED);
        }

        ResponseAPI<String> imageEmbedding = biometricClient.embeddingImage(request.getImage());
        if (imageEmbedding.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_CREATE_FAILED);
        }
    }
}
