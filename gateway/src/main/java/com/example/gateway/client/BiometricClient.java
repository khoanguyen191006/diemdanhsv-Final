package com.example.gateway.client;

import com.example.gateway.dto.common.ResponseAPI;
import com.example.gateway.dto.request.student.StudentImageAndDecodeIdUploadRequest;
import com.example.gateway.dto.response.biometric.BiometricEmbeddingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "biometric", url = "${biometric.baseurl}")
public interface BiometricClient {
    @PostMapping("/api/v1/verifyFace")
    ResponseAPI<BiometricEmbeddingResponse> verifyFace(@RequestBody MultipartFile request);

    @PostMapping("/api/v1/verifyCard")
    ResponseAPI<String> verifyCard(@RequestBody MultipartFile request);

    @PostMapping(
            value = "/api/v1/embeddingImage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseAPI<String> embeddingImage(
            @RequestPart("student_id_hash") String studentIdHash,
            @RequestPart("images") MultipartFile image
    );
}
