package com.example.gateway.service;

import com.example.gateway.dto.request.attendanceSession.AttendanceSessionCreateRequest;

public interface AttendanceSessionService {
    void createSession(AttendanceSessionCreateRequest request);
}

