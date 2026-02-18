package com.example.gateway.dto.request.classes;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassCreateRequest {
    String className;
    String room;
    LocalDate startDate;
    LocalDate endDate;
}
