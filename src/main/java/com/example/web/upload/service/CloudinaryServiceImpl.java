package com.example.web.upload.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements ICloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadCloudinary(MultipartFile multipartFile) {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("upload that bai");
        }
    }

    @Override
    public String uploadVideoCloudinary(MultipartFile multipartFile) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("resource_type", "video");
            params.put("public_id", UUID.randomUUID().toString());

            return cloudinary.uploader().upload(multipartFile.getBytes(), params)
                    .get("url")
                    .toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("upload that bai");
        }
    }
}
