package com.example.academicService.service.impl;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.academicService.entity.AttendanceRecord;
import com.example.academicService.repository.AttendanceRecordRepository;
import com.example.academicService.service.AttendanceRecordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordServiceImpl implements AttendanceRecordService {
    AttendanceRecordRepository attendanceRecordRepository;

    @Override
    public void recordAttendance(AttendanceRecordCreateRequest request) {
        AttendanceRecord record = new AttendanceRecord();
        record.setSessionId(request.getSessionId());
        record.setStudentId(request.getStudentId());
        record.setConfidenceScore(request.getConfidenceScore());
        record.setStatus(request.getStatus());
        record.setCheckInTime(LocalDateTime.now());

        attendanceRecordRepository.save(record);
    }
}
