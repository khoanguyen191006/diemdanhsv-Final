package com.example.gateway.controller.external;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.gateway.service.AttendanceSessionService;
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
@FieldDefaults(level = AccessLevel.PUBLIC)
@RequestMapping("/api/v1/attendanceSession")
public class AttendanceSessionExternalController {
    private final AttendanceSessionService attendanceSessionService;

    @PostMapping
    ResponseAPI<String> createAttendanceSession(@RequestBody AttendanceSessionCreateRequest request) {
        attendanceSessionService.createSession(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }
}