package com.example.academicService.service.impl;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.academicService.entity.AttendanceRecord;
import com.example.academicService.entity.AttendanceSession;
import com.example.academicService.entity.Student;
import com.example.academicService.entity.common.AttendanceStatus;
import com.example.academicService.exception.ApplicationException;
import com.example.academicService.exception.ErrorCode;
import com.example.academicService.repository.AttendanceRecordRepository;
import com.example.academicService.repository.AttendanceSessionRepository;
import com.example.academicService.repository.StudentRepository;
import com.example.academicService.service.AttendanceRecordService;
import com.example.academicService.service.EncryptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceRecordServiceImpl implements AttendanceRecordService {
    AttendanceSessionRepository attendanceSessionRepository;
    AttendanceRecordRepository attendanceRecordRepository;
    StudentRepository studentRepository;
    EncryptionService encryptionService;

    @Override
    public void recordAttendance(AttendanceRecordCreateRequest request) {
        Student student =
                studentRepository.findById(UUID.fromString(encryptionService.decodeStudentId(request.getDecodedStudentId())))
                        .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));

        AttendanceSession attendanceSession =
                attendanceSessionRepository.findById(UUID.fromString(request.getSessionId()))
                        .orElseThrow(() -> new ApplicationException(ErrorCode.ATTENDANCE_SESSION_NOT_FOUND));

        AttendanceRecord record = new AttendanceRecord();
        record.setSessionId(request.getSessionId());
        record.setConfidenceScore(request.getConfidenceScore());
        record.setStatus(resolveAttendanceStatus(attendanceSession));
        record.setCheckInTime(LocalDateTime.now());
        record.setStudentId(student.getId().toString());
        attendanceRecordRepository.save(record);
    }

    private AttendanceStatus resolveAttendanceStatus(AttendanceSession session) {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(session.getStartTime())) {
            throw new ApplicationException(ErrorCode.ATTENDANCE_TOO_EARLY);
        }

        if (now.isAfter(session.getEndTime())) {
            throw new ApplicationException(ErrorCode.ATTENDANCE_TOO_LATE);
        }

        LocalDateTime lateThreshold =
                session.getStartTime().plusMinutes(5);

        if (!now.isAfter(lateThreshold)) {
            return AttendanceStatus.ON_TIME;
        }

        return AttendanceStatus.LATE;
    }
}
