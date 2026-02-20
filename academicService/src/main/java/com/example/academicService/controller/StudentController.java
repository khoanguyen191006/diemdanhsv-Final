package com.example.academicService.controller;

import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.dto.request.student.StudentUploadRequest;
import com.example.academicService.dto.response.student.StudentResponse;
import com.example.academicService.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class StudentController {
    private final StudentService studentService;
    @PostMapping
    ResponseAPI<String> createStudent(@RequestBody StudentUploadRequest request) {
        var result = studentService.createStudent(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/{decodeStudentId}")
    ResponseAPI<StudentResponse> getStudentByDecodeStudentId(@PathVariable String decodeStudentId) {
        var result = studentService.getStudentByDecodeStudentId(decodeStudentId);
        return ResponseAPI.<StudentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
