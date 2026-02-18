package com.example.academicService.entity;

import com.example.academicService.entity.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "class_enrollments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassEnrollment extends AbstractEntity<UUID> {

    @Column(name = "class_id")
    String classId;

    @Column(name = "student_id")
    String studentId;

    @Column(name = "enrolled_at")
    LocalDateTime enrolledAt;

    String status;
}


