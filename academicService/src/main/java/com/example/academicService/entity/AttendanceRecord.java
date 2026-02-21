package com.example.academicService.entity;

import com.example.academicService.entity.common.AbstractEntity;
import com.example.academicService.entity.common.AttendanceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attendance_records")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecord extends AbstractEntity<UUID> {

    @Column(name = "session_id")
    String sessionId;

    @Column(name = "student_id")
    String studentId;

    @Column(name = "confidence_score")
    BigDecimal confidenceScore;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    AttendanceStatus status;

    @Column(name = "check_in_time")
    LocalDateTime checkInTime;
}
