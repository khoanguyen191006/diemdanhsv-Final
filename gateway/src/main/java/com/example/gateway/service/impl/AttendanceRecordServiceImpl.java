package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.gateway.service.AttendanceRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordServiceImpl implements AttendanceRecordService {
    AcademicClient academicClient;

    @Override
    public void recordAttendance(AttendanceRecordCreateRequest request) {
        academicClient.createAttendanceRecord(request);
    }
}
