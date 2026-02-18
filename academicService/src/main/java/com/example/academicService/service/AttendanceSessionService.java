package com.example.academicService.service;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.academicService.dto.request.attendanceSession.AttendanceSessionCreateRequest;

public interface AttendanceSessionService {
    String createSession(AttendanceSessionCreateRequest request);
}
