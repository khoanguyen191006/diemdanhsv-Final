package com.example.academicService.service.impl;

import com.example.academicService.dto.request.student.StudentUploadRequest;
import com.example.academicService.entity.Student;
import com.example.academicService.repository.StudentRepository;
import com.example.academicService.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;

    @Override
    public String createStudent(StudentUploadRequest request) {
        Student student = new Student();
        student.setStudentCode(request.getStudentCode());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setStatus(request.getStatus());
        student.setCreatedAt(LocalDateTime.now());

        studentRepository.save(student);
        return student.getId().toString();
    }
}
