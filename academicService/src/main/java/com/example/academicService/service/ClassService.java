package com.example.academicService.service;

import com.example.academicService.dto.request.classes.ClassCreateRequest;

public interface ClassService {
    String createClass(ClassCreateRequest request);
}

