package com.coffeeconnect.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class MediaService {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    private Path uploadPath;

    private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of("image/jpeg", "image/png", "image/gif", "image/webp");
    private static final Set<String> ALLOWED_AUDIO_TYPES = Set.of("audio/mpeg", "audio/wav", "audio/ogg", "audio/webm");
    private static final Set<String> ALLOWED_VIDEO_TYPES = Set.of("video/mp4", "video/webm", "video/ogg");

    @PostConstruct
    public void init() {
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath.resolve("images"));
            Files.createDirectories(uploadPath.resolve("audio"));
            Files.createDirectories(uploadPath.resolve("voice"));
            Files.createDirectories(uploadPath.resolve("videos"));
            Files.createDirectories(uploadPath.resolve("files"));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directories", e);
        }
    }

    public String uploadFile(MultipartFile file, String subDir) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        try {
            Path targetPath = uploadPath.resolve(subDir).resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + subDir + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public String uploadImage(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_IMAGE_TYPES.contains(mimeType)) {
            throw new RuntimeException("Invalid image type. Allowed: JPEG, PNG, GIF, WebP");
        }
        return uploadFile(file, "images");
    }

    public String uploadAudio(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_AUDIO_TYPES.contains(mimeType)) {
            throw new RuntimeException("Invalid audio type. Allowed: MP3, WAV, OGG, WebM");
        }
        return uploadFile(file, "audio");
    }

    public String uploadVoice(MultipartFile file) {
        return uploadFile(file, "voice");
    }

    public String uploadVideo(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null || !ALLOWED_VIDEO_TYPES.contains(mimeType)) {
            throw new RuntimeException("Invalid video type. Allowed: MP4, WebM, OGG");
        }
        return uploadFile(file, "videos");
    }

    public String uploadGeneric(MultipartFile file) {
        return uploadFile(file, "files");
    }

    public Resource loadFileAsResource(String filePath) {
        try {
            Path file = uploadPath.resolve(filePath).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }
}
