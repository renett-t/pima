package ru.kpfu.itis.renett.service.fileService;

import ru.kpfu.itis.renett.exceptions.FileUploadException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileManagerImpl implements FileManager {
    private String storageUrl;

    public FileManagerImpl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    @Override
    public String saveFile(String fileName, String contextPath, InputStream fileInputStream) {
        String randomizedFileName = getNameForFile(fileName);
        upload(randomizedFileName, contextPath, fileInputStream);

        return randomizedFileName;
    }

    private String getNameForFile(String fileName) {
        return (int) (Math.random() * (17777777 - 100) + 100) + fileName;
    }

    private void upload(String filename, String contextPath, InputStream fileInputStream) {
        try {
            String uploadPath = contextPath + File.separator + storageUrl;
            File fileToSave = new File(uploadPath + File.separator + filename);
            if (!fileToSave.getParentFile().exists()) {
                fileToSave.getParentFile().mkdirs();
            }
            Files.copy(fileInputStream, fileToSave.toPath());
        } catch (IOException e) {
            throw new FileUploadException("Проблема при отправке изображения, попробуйте ещё разочек", e);
        }
    }
}
