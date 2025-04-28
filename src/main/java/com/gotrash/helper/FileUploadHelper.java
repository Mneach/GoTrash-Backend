package com.gotrash.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadHelper {

  @Value("${file.upload.base.dir}")
  public String BASE_DIR;
  @Value("${file.upload.base.url}")
  public String BASE_URL;

  public String uploadFile(String entityName, String identifier, MultipartFile imageFile, String oldImageUrl) throws IOException {
    // If there's a new file and an old file exists, delete the old one
    if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
      deleteOldFile(oldImageUrl);
    }

    if (imageFile != null && !imageFile.isEmpty()) {
      identifier = sanitizeFileName(identifier);

      // Build the upload path
      Path uploadPath = Paths.get(System.getProperty("user.dir"), BASE_DIR, entityName, identifier);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath); // Create directories if they don't exist
      }

      // Sanitize original filename
      String originalFileName = imageFile.getOriginalFilename();
      String safeFileName = sanitizeFileName(originalFileName);

      // Create unique file name using UUID
      String fileName = System.currentTimeMillis() + "_" + safeFileName;
      Path filePath = uploadPath.resolve(fileName);

      // Save the file to the local path
      imageFile.transferTo(filePath.toFile());

      // Return the relative path (e.g., citizen/123/12513213123245-filename.jpg)
      return entityName + "/" + identifier + "/" + fileName;
    }

    return null;
  }

  private void deleteOldFile(String oldImageUrl) throws IOException {
    // Remove BASE_URL + "/images/" from the oldImageUrl
    String relativePath = oldImageUrl.replace(BASE_URL + "/images/", "");

    // Build the real file path (local system path)
    Path oldFilePath = Paths.get(System.getProperty("user.dir"), BASE_DIR, relativePath);
    File oldFile = oldFilePath.toFile();

    if (oldFile.exists()) {
      if (oldFile.delete()) {
        System.out.println("Old file deleted: " + oldFile.getAbsolutePath());
      } else {
        throw new IOException("Failed to delete old file: " + oldFile.getAbsolutePath());
      }
    }
  }

  public String generateFileUrl(String filePath) {
    return BASE_URL + "/" + BASE_DIR + "/" + filePath;
  }


  private String sanitizeFileName(String originalFileName) {
    if (originalFileName == null) {
      return "unknown";
    }
    // Replace spaces with underscores, remove unsafe characters
    return originalFileName
        .trim()
        .replaceAll("\\s+", "_") // Replace spaces with underscores
        .replaceAll("[^a-zA-Z0-9\\-_.@]", "") // Allow only alphanumeric, -, _, ., and @
        .toLowerCase(); // Convert to lowercase
  }
}
