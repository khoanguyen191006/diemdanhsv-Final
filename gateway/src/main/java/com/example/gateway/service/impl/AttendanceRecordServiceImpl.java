package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.client.BiometricClient;
import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateAndVerifyFaceRequest;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.gateway.dto.response.biometric.BiometricEmbeddingResponse;
import com.example.gateway.exception.ApplicationException;
import com.example.gateway.exception.ErrorCode;
import com.example.gateway.service.AttendanceRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordServiceImpl implements AttendanceRecordService {
    AcademicClient academicClient;
    BiometricClient biometricClient;

    @Override
    public void recordAttendance(AttendanceRecordCreateAndVerifyFaceRequest request) {
        ResponseAPI<BiometricEmbeddingResponse> biometricEmbeddingResponse = biometricClient.verifyFace(request.getImage());

        if (biometricEmbeddingResponse.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.STUDENT_NOT_FOUND);
        }

        ResponseAPI<String> result =
                academicClient.createAttendanceRecord(createRequest(biometricEmbeddingResponse.getData(), request));

        if (result.getCode() != HttpStatus.OK.value()) {
            throw new ApplicationException(ErrorCode.ATTENDANCE_RECORD_CREATE_FAILED);
        }
    }

    private AttendanceRecordCreateRequest createRequest(BiometricEmbeddingResponse biometricEmbeddingResponse,
                                                        AttendanceRecordCreateAndVerifyFaceRequest request) {
        return AttendanceRecordCreateRequest.builder()
                .decodedStudentId(biometricEmbeddingResponse.getStudentIdHash())
                .confidenceScore(biometricEmbeddingResponse.getConfidence())
                .sessionId(request.getSessionId())
                .build();
    }
}
