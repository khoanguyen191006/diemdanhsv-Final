package com.example.gateway.controller.external;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.student.StudentImageUploadRequest;
import com.example.gateway.dto.request.student.StudentUploadRequest;
import com.example.gateway.dto.response.student.StudentInfoResponse;
import com.example.gateway.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class StudentExternalController {
    private final StudentService studentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseAPI<String> createStudent(@ModelAttribute StudentUploadRequest request) {
        studentService.createStudent(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }

    @PostMapping( value = "/verifyFace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseAPI<StudentInfoResponse> verifyFace(@ModelAttribute StudentImageUploadRequest request) {
        var result = studentService.verifyFace(request);
        return ResponseAPI.<StudentInfoResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
