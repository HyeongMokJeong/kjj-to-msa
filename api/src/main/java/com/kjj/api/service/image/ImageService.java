package com.kjj.api.service.image;

import com.kjj.api.exception.exceptions.UploadFileException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String makeFilePath(String filename, String uploadDir);
    String uploadImage(MultipartFile file, String uploadDir) throws UploadFileException;
    void updateImage(MultipartFile file, String oldPath) throws UploadFileException;
}
