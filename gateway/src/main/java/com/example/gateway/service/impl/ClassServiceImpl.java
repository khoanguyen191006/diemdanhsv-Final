package com.example.gateway.service.impl;

import com.example.gateway.client.AcademicClient;
import com.example.gateway.dto.request.classes.ClassCreateRequest;
import com.example.gateway.service.ClassService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassServiceImpl implements ClassService {
    AcademicClient academicClient;

    @Override
    public void createClass(ClassCreateRequest request) {
        academicClient.createClass(request);
    }
}
