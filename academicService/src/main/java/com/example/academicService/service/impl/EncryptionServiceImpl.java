package com.example.academicService.service.impl;

import com.example.academicService.service.EncryptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EncryptionServiceImpl implements EncryptionService {
    @Value("${spring.security.studentIdHashCode}")
    String STUDENT_ID_HASH_CODE;
    static String HMAC_ALGORITHM = "HmacSHA256";

    @Override
    public String encodeStudentId(String studentId) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey =
                    new SecretKeySpec(STUDENT_ID_HASH_CODE.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);

            mac.init(secretKey);
            byte[] rawHmac = mac.doFinal(studentId.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().withoutPadding().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Encode studentId failed", e);
        }
    }

    @Override
    public String decodeStudentId(String encodedStudentId) {
        throw new UnsupportedOperationException("HMAC cannot be decoded");
    }
}