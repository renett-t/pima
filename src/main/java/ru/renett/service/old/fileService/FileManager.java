package ru.renett.service.old.fileService;

import java.io.InputStream;

public interface FileManager {
    String saveFile(String imageFileName, String contextPath, InputStream inputStream);
}
