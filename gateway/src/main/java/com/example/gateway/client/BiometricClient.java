package com.example.gateway.client;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.student.StudentImageAndDecodeIdUploadRequest;
import com.example.gateway.dto.response.biometric.BiometricEmbeddingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "biometric", url = "${biometric.baseurl}")
public interface BiometricClient {
    @PostMapping("/attendance_system/api/v1/verifyFace")
    ResponseAPI<BiometricEmbeddingResponse> verifyFace(@RequestBody MultipartFile request);

    @PostMapping("/attendance_system/api/v1/verifyCard")
    ResponseAPI<String> verifyCard(@RequestBody MultipartFile request);

    @PostMapping("/attendance_system/api/v1/embeddingImage")
    ResponseAPI<String> embeddingImage(@RequestBody StudentImageAndDecodeIdUploadRequest request);
}
