package com.example.gateway.client;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.attendanceRecord.AttendanceRecordCreateRequest;
import com.example.gateway.dto.request.attendanceSession.AttendanceSessionCreateRequest;
import com.example.gateway.dto.request.classes.ClassCreateRequest;
import com.example.gateway.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.gateway.dto.request.student.StudentInfoUploadRequest;
import com.example.gateway.dto.response.student.StudentInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/api/v1/student")
    ResponseAPI<String> createStudent(@RequestBody StudentInfoUploadRequest request);

    @GetMapping("/api/v1/student/{decodeStudentId}")
    ResponseAPI<StudentInfoResponse> getStudentByDecodeStudentId(@PathVariable String decodeStudentId);
}