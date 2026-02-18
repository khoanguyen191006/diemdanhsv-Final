package com.example.academicService.service.impl;

import com.example.academicService.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.academicService.entity.AttendanceSession;
import com.example.academicService.repository.AttendanceSessionRepository;
import com.example.academicService.service.AttendanceSessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceSessionServiceImpl implements AttendanceSessionService {
    AttendanceSessionRepository attendanceSessionRepository;

    @Override
    public String createSession(AttendanceSessionCreateRequest request) {
        AttendanceSession session = new AttendanceSession();
        session.setClassId(request.getClassId());
        session.setSessionDate(request.getSessionDate());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());

        attendanceSessionRepository.save(session);
        return session.getId().toString();
    }
}

