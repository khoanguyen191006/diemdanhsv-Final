package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.gateway.service.AttendanceSessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceSessionServiceImpl implements AttendanceSessionService {
    AcademicClient academicClient;

    @Override
    public void createSession(AttendanceSessionCreateRequest request) {
        academicClient.createAttendanceSession(request);
    }
}
