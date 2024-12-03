package ru.krupnoveo.edu.user_service.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {
    InputStreamResource downloadPhoto(String photoName);

    String uploadPhoto(MultipartFile file, String oldPhotoName);

    void deletePhoto(String photoName);
}
