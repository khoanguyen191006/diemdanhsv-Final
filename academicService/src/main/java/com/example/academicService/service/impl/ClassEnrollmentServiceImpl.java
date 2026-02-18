package com.example.academicService.service.impl;

import com.example.academicService.dto.request.enrollStudent.EnrollStudentRequest;
import com.example.academicService.entity.ClassEnrollment;
import com.example.academicService.repository.ClassEnrollmentRepository;
import com.example.academicService.service.ClassEnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassEnrollmentServiceImpl implements ClassEnrollmentService {
    ClassEnrollmentRepository classEnrollmentRepository;

    @Override
    public void enroll(EnrollStudentRequest request) {
        ClassEnrollment entity = new ClassEnrollment();
        entity.setClassId(request.getClassId());
        entity.setStudentId(request.getStudentId());
        entity.setStatus("ACTIVE");
        entity.setEnrolledAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());

        classEnrollmentRepository.save(entity);
    }
}
