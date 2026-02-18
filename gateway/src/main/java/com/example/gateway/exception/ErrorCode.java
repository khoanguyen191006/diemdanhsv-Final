package com.example.gateway.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // =========================
    // Common / Client Errors
    // =========================
    UNAUTHORIZED(401, "Hành động không được phép", HttpStatus.UNAUTHORIZED),
    INVALID_URL(401, "Đường link không hợp lệ", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Truy cập bị từ chối", HttpStatus.FORBIDDEN),
    BAD_REQUEST(400, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),

    // =========================
    // Token / Authentication (4000+)
    // =========================
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
    // Class / Enrollment (4200+)
    // =========================
    CLASS_NOT_FOUND(4201, "Không tìm thấy lớp học", HttpStatus.NOT_FOUND),
    CLASS_ALREADY_EXISTS(4202, "Lớp học đã tồn tại", HttpStatus.CONFLICT),
    CLASS_CREATE_FAILED(4203, "Tạo lớp học thất bại", HttpStatus.BAD_REQUEST),

    STUDENT_NOT_ENROLLED(4204, "Sinh viên chưa đăng ký lớp học", HttpStatus.BAD_REQUEST),
    CLASS_ENROLLMENT_EXISTS(4205, "Sinh viên đã đăng ký lớp học này", HttpStatus.CONFLICT),
    CLASS_ENROLLMENT_FAILED(4206, "Đăng ký lớp học thất bại", HttpStatus.BAD_REQUEST),

    // =========================
    // Attendance (4300+)
    // =========================
    ATTENDANCE_SESSION_NOT_FOUND(4301, "Không tìm thấy buổi điểm danh", HttpStatus.NOT_FOUND),
    ATTENDANCE_SESSION_CREATE_FAILED(4302, "Tạo buổi điểm danh thất bại", HttpStatus.BAD_REQUEST),

    ATTENDANCE_RECORD_EXISTS(4303, "Sinh viên đã được điểm danh", HttpStatus.CONFLICT),
    ATTENDANCE_RECORD_CREATE_FAILED(4304, "Không thể lưu bản ghi điểm danh", HttpStatus.BAD_REQUEST),

    ATTENDANCE_OUT_OF_TIME_RANGE(4305, "Ngoài thời gian điểm danh", HttpStatus.BAD_REQUEST),

    // =========================
    // Biometric / Face / Image (4400+)
    // =========================
    IMAGE_INVALID(4401, "Ảnh được đưa vào hệ thống bị lỗi", HttpStatus.BAD_REQUEST),
    IMAGE_EMPTY(4402, "Ảnh rỗng hoặc không tồn tại", HttpStatus.BAD_REQUEST),
    IMAGE_FORMAT_NOT_SUPPORTED(4403, "Định dạng ảnh không được hỗ trợ", HttpStatus.BAD_REQUEST),

    FACE_NOT_DETECTED(4404, "Không phát hiện được khuôn mặt", HttpStatus.BAD_REQUEST),
    MULTIPLE_FACES_DETECTED(4405, "Phát hiện nhiều khuôn mặt trong ảnh", HttpStatus.BAD_REQUEST),

    BIOMETRIC_MATCH_FAILED(4406, "Không nhận diện được sinh viên", HttpStatus.BAD_REQUEST),
    BIOMETRIC_SERVICE_UNAVAILABLE(4407, "Dịch vụ sinh trắc học không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),

    EMBEDDING_CREATE_FAILED(4408, "Không tạo được embedding khuôn mặt", HttpStatus.INTERNAL_SERVER_ERROR),

    // =========================
    // File / Upload (4500+)
    // =========================
    FILE_UPLOAD_FAILED(4501, "Tải file lên thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_TOO_LARGE(4502, "Dung lượng file vượt quá giới hạn", HttpStatus.BAD_REQUEST),
    FILE_STORAGE_ERROR(4503, "Lỗi lưu trữ file", HttpStatus.INTERNAL_SERVER_ERROR),

    // =========================
    // Infrastructure / System (5000+)
    // =========================
    DATABASE_ERROR(5001, "Lỗi hệ thống cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_TIMEOUT(5002, "Hết thời gian chờ dịch vụ bên ngoài", HttpStatus.GATEWAY_TIMEOUT),
    SERVICE_UNAVAILABLE(5003, "Dịch vụ tạm thời không khả dụng", HttpStatus.SERVICE_UNAVAILABLE),
    INTERNAL_SERVER_ERROR(5004, "Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}