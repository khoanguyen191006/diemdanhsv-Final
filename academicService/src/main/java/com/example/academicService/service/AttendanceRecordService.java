package com.example.academicService.service;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;

public interface AttendanceRecordService {
    void recordAttendance(AttendanceRecordCreateRequest request);
}
