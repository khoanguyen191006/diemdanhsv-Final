package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.client.BiometricClient;
import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.student.StudentImageAndDecodeIdUploadRequest;
import com.example.gateway.dto.request.student.StudentImageUploadRequest;
import com.example.gateway.dto.request.student.StudentInfoUploadRequest;
import com.example.gateway.dto.request.student.StudentUploadRequest;
import com.example.gateway.dto.response.biometric.BiometricEmbeddingResponse;
import com.example.gateway.dto.response.student.StudentInfoResponse;
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
        ResponseAPI<String> decodeStudentId =
                academicClient.createStudent(buildeStudentInfoUploadRequest(request));

        if (decodeStudentId.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_CREATE_FAILED);
        }

        ResponseAPI<String> imageEmbedding =
                biometricClient.embeddingImage(buildStudentImageAndDecodeIdUploadRequest(decodeStudentId.getData() , request));

        if (imageEmbedding.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.IMAGE_INVALID);
        }
    }

    private StudentInfoUploadRequest buildeStudentInfoUploadRequest(StudentUploadRequest request) {
        return StudentInfoUploadRequest.builder()
                .studentCode(request.getStudentCode())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .status(request.getStatus())
                .build();
    }

    private StudentImageAndDecodeIdUploadRequest buildStudentImageAndDecodeIdUploadRequest(String studentId,
                                                                                           StudentUploadRequest request) {
        return StudentImageAndDecodeIdUploadRequest.builder()
                .studentIdHash(studentId)
                .image(request.getImage())
                .build();
    }

    @Override
    public StudentInfoResponse verifyFace(StudentImageUploadRequest request) {
        ResponseAPI<BiometricEmbeddingResponse> decodeStudentId = biometricClient.verifyFace(request.getImage());

        if (decodeStudentId.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_NOT_FOUND);
        }

        ResponseAPI<StudentInfoResponse> studentInfoResponse =
                academicClient.getStudentByDecodeStudentId(decodeStudentId.getData().getStudentIdHash());

        if (studentInfoResponse.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_NOT_FOUND);
        }

        return studentInfoResponse.getData();
    }
}
