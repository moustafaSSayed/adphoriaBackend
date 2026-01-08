package com.app.service.impl;

import com.app.service.FileStorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    @Value("${cloudinary.api-key:}")
    private String apiKey;

    @Value("${cloudinary.api-secret:}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        if (cloudName != null && !cloudName.isEmpty() &&
                apiKey != null && !apiKey.isEmpty() &&
                apiSecret != null && !apiSecret.isEmpty()) {

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloudName,
                    "api_key", apiKey,
                    "api_secret", apiSecret,
                    "secure", true));
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        if (cloudinary == null) {
            throw new RuntimeException(
                    "Cloudinary is not configured. Please set CLOUDINARY_CLOUD_NAME, CLOUDINARY_API_KEY, and CLOUDINARY_API_SECRET environment variables.");
        }

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto"));

            return (String) uploadResult.get("secure_url");
        } catch (IOException ex) {
            throw new RuntimeException("Could not upload file to Cloudinary: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty() || cloudinary == null) {
            return;
        }

        try {
            // Extract public_id from Cloudinary URL
            String publicId = extractPublicId(fileUrl);
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (IOException ex) {
            // Log error but don't throw - file might already be deleted
        }
    }

    private String extractPublicId(String url) {
        if (url == null || !url.contains("cloudinary.com")) {
            return null;
        }

        try {
            // URL format:
            // https://res.cloudinary.com/{cloud_name}/image/upload/v{version}/{public_id}.{format}
            String[] parts = url.split("/upload/");
            if (parts.length > 1) {
                String pathWithVersion = parts[1];
                // Remove version prefix (v1234567890/)
                if (pathWithVersion.contains("/")) {
                    pathWithVersion = pathWithVersion.substring(pathWithVersion.indexOf("/") + 1);
                }
                // Remove file extension
                int lastDot = pathWithVersion.lastIndexOf(".");
                if (lastDot > 0) {
                    return pathWithVersion.substring(0, lastDot);
                }
                return pathWithVersion;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
