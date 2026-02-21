package com.example.academicService.controller;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendanceRecord")
public class AttendanceRecordController {
    private final AttendanceRecordService attendanceRecordService;

    @PostMapping
    ResponseAPI<String> createAttendanceRecord(@RequestBody AttendanceRecordCreateRequest request) {
        attendanceRecordService.recordAttendance(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }
}
