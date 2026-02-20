package com.example.academicService.service.impl;

import com.example.academicService.service.EncryptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EncryptionServiceImpl implements EncryptionService {

    static final String AES_ALGORITHM = "AES";
    static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    static final int GCM_IV_LENGTH = 12;
    static final int GCM_TAG_LENGTH = 128;

    @Value("${spring.security.studentIdSecret}")
    String secret;

    @Override
    public String encodeStudentId(String studentId) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            SecretKeySpec key = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    AES_ALGORITHM
            );

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    new GCMParameterSpec(GCM_TAG_LENGTH, iv)
            );

            byte[] encrypted = cipher.doFinal(studentId.getBytes(StandardCharsets.UTF_8));

            // concat IV + encrypted
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(result);

        } catch (Exception e) {
            throw new RuntimeException("Encode studentId failed", e);
        }
    }

    @Override
    public String decodeStudentId(String encodedStudentId) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(encodedStudentId);

            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encrypted = new byte[decoded.length - GCM_IV_LENGTH];

            System.arraycopy(decoded, 0, iv, 0, iv.length);
            System.arraycopy(decoded, iv.length, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            SecretKeySpec key = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    AES_ALGORITHM
            );

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    new GCMParameterSpec(GCM_TAG_LENGTH, iv)
            );

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Decode studentId failed", e);
        }
    }
}