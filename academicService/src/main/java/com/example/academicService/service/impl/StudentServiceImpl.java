package com.example.academicService.service.impl;

import com.example.academicService.dto.request.student.StudentUploadRequest;
import com.example.academicService.dto.response.student.StudentResponse;
import com.example.academicService.entity.Student;
import com.example.academicService.exception.ApplicationException;
import com.example.academicService.exception.ErrorCode;
import com.example.academicService.mapper.StudentMapper;
import com.example.academicService.repository.StudentRepository;
import com.example.academicService.service.EncryptionService;
import com.example.academicService.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    EncryptionService encryptionService;

    @Override
    public String createStudent(StudentUploadRequest request) {
        Student student = new Student();
        student.setStudentCode(request.getStudentCode());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setStatus(request.getStatus());
        student.setCreatedAt(LocalDateTime.now());
        studentRepository.save(student);

        return encryptionService.encodeStudentId(student.getId().toString());
    }

    @Override
    public StudentResponse getStudentByDecodeStudentId(String decodeStudentId) {
        String studentId = encryptionService.decodeStudentId(decodeStudentId);
        Student student = studentRepository.findById(UUID.fromString(studentId))
                .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));
        return StudentMapper.toStudentResponse(student);
    }
}
