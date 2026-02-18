package com.example.academicService.entity;

import com.example.academicService.entity.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "attendance_sessions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceSession extends AbstractEntity<UUID> {

    @Column(name = "class_id")
    String classId;

    @Column(name = "session_date")
    LocalDateTime sessionDate;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;
}
