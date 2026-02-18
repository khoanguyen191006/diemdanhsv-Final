package com.example.gateway.client;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.gateway.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.gateway.dto.request.classes.ClassCreateRequest;
import com.example.gateway.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.gateway.dto.request.student.StudentUploadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "academic", url = "${academic.baseurl}")
public interface AcademicClient {
    @PostMapping("/api/v1/attendanceRecord")
    ResponseAPI<String> createAttendanceRecord(@RequestBody AttendanceRecordCreateRequest request);

    @PostMapping("/api/v1/attendanceSession")
    ResponseAPI<String> createAttendanceSession(@RequestBody AttendanceSessionCreateRequest request);

    @PostMapping("/api/v1/class")
    ResponseAPI<String> createClass(@RequestBody ClassCreateRequest request);

    @PostMapping("/api/v1/classEnrollment")
    ResponseAPI<String> createClassEnrollment(@RequestBody EnrollStudentRequest request);

    @PostMapping("/api/v1/studentService")
    ResponseAPI<String> createStudent(@RequestBody StudentUploadRequest request);
}