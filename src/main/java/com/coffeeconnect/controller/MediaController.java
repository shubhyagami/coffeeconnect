package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.MediaService;
import com.coffeeconnect.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;
    private final UserService userService;

    public MediaController(MediaService mediaService, UserService userService) {
        this.mediaService = mediaService;
        this.userService = userService;
    }

    @PostMapping("/upload/image")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String url = mediaService.uploadImage(file);
            return ResponseEntity.ok(Map.of("url", url, "message", "Image uploaded"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload/audio")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadAudio(@RequestParam("file") MultipartFile file,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String url = mediaService.uploadAudio(file);
            return ResponseEntity.ok(Map.of("url", url, "message", "Audio uploaded"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload/voice")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadVoice(@RequestParam("file") MultipartFile file,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String url = mediaService.uploadVoice(file);
            return ResponseEntity.ok(Map.of("url", url, "message", "Voice recording saved"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload/video")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadVideo(@RequestParam("file") MultipartFile file,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String url = mediaService.uploadVideo(file);
            return ResponseEntity.ok(Map.of("url", url, "message", "Video uploaded"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/file/{type}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String type, @PathVariable String filename) {
        Resource resource = mediaService.loadFileAsResource(type + "/" + filename);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
