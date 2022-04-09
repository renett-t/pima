package ru.kpfu.itis.renett.service.fileService;

import java.io.InputStream;

public interface FileManager {
    String saveFile(String imageFileName, String contextPath, InputStream inputStream);
}
