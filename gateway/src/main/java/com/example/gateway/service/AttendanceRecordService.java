package com.example.gateway.service;

import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateRequest;

public interface AttendanceRecordService {
    void recordAttendance(AttendanceRecordCreateRequest request);
}
