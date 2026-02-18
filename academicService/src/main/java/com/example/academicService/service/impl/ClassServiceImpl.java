package com.example.academicService.service.impl;

import com.example.academicService.dto.request.classes.ClassCreateRequest;
import com.example.academicService.entity.ClassEntity;
import com.example.academicService.repository.ClassRepository;
import com.example.academicService.service.ClassService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassServiceImpl implements ClassService {
    ClassRepository classRepository;

    @Override
    public String createClass(ClassCreateRequest request) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(request.getClassName());
        classEntity.setRoom(request.getRoom());
        classEntity.setStartDate(request.getStartDate());
        classEntity.setEndDate(request.getEndDate());

        classRepository.save(classEntity);
        return classEntity.getId().toString();
    }
}
