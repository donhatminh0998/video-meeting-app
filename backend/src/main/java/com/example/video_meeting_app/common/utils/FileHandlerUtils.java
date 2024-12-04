package com.example.video_meeting_app.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileHandlerUtils {
    public String saveFile(
            String path,
            String filenameBase,
            MultipartFile file
    ) {
        //backend/media
        String pathDir = String.format("media/%s", path);
        try {
            Files.createDirectories(Path.of(pathDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String originalFilename = file.getOriginalFilename();
        String[] fileNameSplit = originalFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String filename = filenameBase + "." + extension;
        String filePath = pathDir + filename;

        try {
            file.transferTo(Path.of(filePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return String.format("/static/%s", path + filename);
    }
    public void deleteFile(String relativePath) {
        try {
            String filePath = relativePath.replaceFirst("/static", "media");
            Files.deleteIfExists(Paths.get(filePath));
            log.info("Deleted file: {}", filePath);
        } catch (Exception e) {
            log.info("Error deleting file: {}", relativePath, e);
        }
    }
}