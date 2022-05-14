package ru.renett.service.file;

import java.io.InputStream;

public interface FileManager {
    String saveFile(String imageFileName, String contextPath, InputStream inputStream);
}
