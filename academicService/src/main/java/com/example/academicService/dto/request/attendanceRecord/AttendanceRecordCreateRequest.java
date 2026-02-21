package com.example.academicService.dto.request.attendanceRecord;

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
    String decodedStudentId;
    BigDecimal confidenceScore;
}

