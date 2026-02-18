package com.example.academicService.controller;

import com.example.academicService.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.academicService.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
