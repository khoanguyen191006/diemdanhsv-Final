package com.example.academicService.repository;

import com.example.academicService.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, UUID> {
}
