package com.example.academicService.mapper;

import com.example.academicService.dto.response.student.StudentResponse;
import com.example.academicService.entity.Student;

import java.util.List;

public class StudentMapper {

    public static StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .studentCode(student.getStudentCode())
                .status(student.getStatus())
                .email(student.getEmail())
                .fullName(student.getFullName())
                .build();
    }

    public static List<StudentResponse> studentResponses(List<Student> studentList) {
        return studentList.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();
    }

}
