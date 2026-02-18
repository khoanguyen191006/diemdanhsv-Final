package com.example.academicService.controller;

import com.example.academicService.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.academicService.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.service.AttendanceSessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendanceSession")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AttendanceSessionController {
    private final AttendanceSessionService attendanceSessionService;

    @PostMapping
    ResponseAPI<String> createAttendanceSession(@RequestBody AttendanceSessionCreateRequest request) {
        var result = attendanceSessionService.createSession(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
