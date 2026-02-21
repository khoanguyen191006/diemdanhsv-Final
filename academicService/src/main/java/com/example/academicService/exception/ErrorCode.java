package com.example.academicService.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    //Client Errors (4xx)
    UNAUTHORIZED(401, "Hành động không được phép", HttpStatus.UNAUTHORIZED),
    INVALID_URL(401, "Đường link không hợp lệ", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),

    // Token related errors (grouped together)
    TOKEN_INVALID(4001, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(4002, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    TOKEN_BLACKLISTED(4003, "Token nằm trong danh sách đen", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(4004, "Refresh token không hợp lệ", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(4005, "Refresh token đã hết hạn", HttpStatus.UNAUTHORIZED),
    JWT_SECRET_NOT_CONFIGURED(4006, "Chưa cấu hình JWT secret", HttpStatus.INTERNAL_SERVER_ERROR),

    // =========================
    // Student (4100+)
    // =========================
    STUDENT_NOT_FOUND(4101, "Không tìm thấy sinh viên", HttpStatus.NOT_FOUND),
    STUDENT_ALREADY_EXISTS(4102, "Sinh viên đã tồn tại", HttpStatus.CONFLICT),
    STUDENT_CREATE_FAILED(4103, "Sinh viên không được tạo", HttpStatus.BAD_REQUEST),
    STUDENT_UPDATE_FAILED(4104, "Cập nhật sinh viên thất bại", HttpStatus.BAD_REQUEST),

    // =========================
    // Attendance (4300+)
    // =========================
    ATTENDANCE_SESSION_NOT_FOUND(4301, "Không tìm thấy buổi điểm danh", HttpStatus.NOT_FOUND),
    ATTENDANCE_SESSION_CREATE_FAILED(4302, "Tạo buổi điểm danh thất bại", HttpStatus.BAD_REQUEST),

    ATTENDANCE_RECORD_EXISTS(4303, "Sinh viên đã được điểm danh", HttpStatus.CONFLICT),
    ATTENDANCE_RECORD_CREATE_FAILED(4304, "Không thể lưu bản ghi điểm danh", HttpStatus.BAD_REQUEST),

    ATTENDANCE_OUT_OF_TIME_RANGE(4305, "Ngoài thời gian điểm danh", HttpStatus.BAD_REQUEST),
    ATTENDANCE_TOO_EARLY(4305, "Chưa đến thời gian điểm danh", HttpStatus.BAD_REQUEST),
    ATTENDANCE_TOO_LATE(4306, "Đã quá thời gian điểm danh", HttpStatus.BAD_REQUEST);

    public String formatMessage(Object... args) {
        return String.format(message, args);
    }

    int code;
    String message;
    HttpStatus httpStatus;
}

