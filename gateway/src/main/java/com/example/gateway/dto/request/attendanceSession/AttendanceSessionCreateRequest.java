package com.example.gateway.dto.request.attendanceSession;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceSessionCreateRequest {
    String classId;
    LocalDateTime sessionDate;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
