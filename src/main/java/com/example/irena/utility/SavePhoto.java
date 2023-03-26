package com.example.irena.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class SavePhoto {
    private String savePhoto(MultipartFile file) throws IOException {
        String linkPhoto = "F:\\Java lessons\\codingBat\\src\\main\\resources\\static\\images";
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            String[] split = originalFileName.split("\\.");
            String randomName = UUID.randomUUID().toString() + "." + split[split.length - 1];
            Path path = Paths.get(linkPhoto + "/" + randomName);
            Files.copy(file.getInputStream(), path);
            return randomName;
        }
        return null;
    }
}
