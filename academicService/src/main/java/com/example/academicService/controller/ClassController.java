package com.example.academicService.controller;

import com.example.academicService.dto.request.classes.ClassCreateRequest;
import com.example.academicService.dto.response.common.ResponseAPI;
import com.example.academicService.service.ClassService;
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
@RequestMapping("/api/v1/class")
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ClassController {
    private final ClassService classService;

    @PostMapping
    ResponseAPI<String> createClass(@RequestBody ClassCreateRequest request) {
        var result = classService.createClass(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}
