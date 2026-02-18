package com.example.academicService.exception;

import com.example.academicService.dto.response.common.ResponseAPI;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseAPI<String>> handlingRunTimeException(RuntimeException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseAPI.<String>builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(message)
                        .data(message)
                        .build()
        );
    }

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ResponseAPI<?>> handlingAppException(ApplicationException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        HttpStatus status = (errorCode != null && errorCode.getHttpStatus() != null) ? errorCode.getHttpStatus() : HttpStatus.BAD_REQUEST;
        String message = exception.getMessage();
        return ResponseEntity.status(status).body(
                ResponseAPI.<String>builder()
                        .code(status.value())
                        .message(message)
                        .data(message)
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseAPI<?>> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .map(error -> (error instanceof FieldError fieldError)
                        ? (fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        : error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ResponseAPI.<String>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .data(message)
                        .build()
        );
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseAPI<?>> handleConstraintViolation(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ResponseAPI.<String>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .data(message)
                        .build()
        );
    }
}
