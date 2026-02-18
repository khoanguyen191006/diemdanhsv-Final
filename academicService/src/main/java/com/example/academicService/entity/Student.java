package com.example.academicService.entity;

import com.example.academicService.entity.common.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student extends AbstractEntity<UUID> {
    String studentCode;
    String fullName;
    String email;
    String status;
}
