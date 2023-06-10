package com.example.web.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    String uploadCloudinary(MultipartFile multipartFile);

    String uploadVideoCloudinary(MultipartFile multipartFile);
}
