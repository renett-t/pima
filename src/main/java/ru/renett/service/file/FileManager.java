package ru.renett.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileManager {
    String saveFile(String imageFileName, InputStream inputStream);
    String saveFile(MultipartFile multipart);
}
