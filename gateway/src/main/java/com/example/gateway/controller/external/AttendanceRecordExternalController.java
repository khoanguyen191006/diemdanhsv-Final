package com.example.gateway.controller.external;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateAndVerifyFaceRequest;
import com.example.gateway.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendanceRecord")
public class AttendanceRecordExternalController {
    private final AttendanceRecordService attendanceRecordService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseAPI<String> createAttendanceRecord(@ModelAttribute AttendanceRecordCreateAndVerifyFaceRequest request) {
        attendanceRecordService.recordAttendance(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }
}
