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
    JWT_SECRET_NOT_CONFIGURED(4006, "Chưa cấu hình JWT secret", HttpStatus.INTERNAL_SERVER_ERROR);

    public String formatMessage(Object... args) {
        return String.format(message, args);
    }

    int code;
    String message;
    HttpStatus httpStatus;
}

