package com.example.gateway.controller.external;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.classes.ClassCreateRequest;
import com.example.gateway.service.ClassService;
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
public class ClassExternalController {
    private final ClassService classService;

    @PostMapping
    ResponseAPI<String> createClass(@RequestBody ClassCreateRequest request) {
        classService.createClass(request);
        return ResponseAPI.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }
}
