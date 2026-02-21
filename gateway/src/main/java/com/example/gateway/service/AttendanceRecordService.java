package com.example.gateway.service;

import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateAndVerifyFaceRequest;

public interface AttendanceRecordService {
    void recordAttendance(AttendanceRecordCreateAndVerifyFaceRequest request);
}
