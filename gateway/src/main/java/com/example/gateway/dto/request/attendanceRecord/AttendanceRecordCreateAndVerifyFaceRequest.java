package com.example.gateway.dto.request.attendanceRecord;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRecordCreateAndVerifyFaceRequest {
    String sessionId;
    MultipartFile image;
}

