package com.example.gateway.dto.request.attendanceRecord;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecordCreateRequest {
    String sessionId;
    String studentId;
    BigDecimal confidenceScore;
    String status;
}

