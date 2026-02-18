package com.example.academicService.repository;

import com.example.academicService.entity.ClassEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, UUID> {
}
