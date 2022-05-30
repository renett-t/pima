package ru.renett.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.renett.exceptions.FileUploadException;
import ru.renett.service.file.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileManagerImpl implements FileManager {
    private final static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Value("${defaults.storage-path}")
    private String storageUrl;

    @Override
    public String saveFile(String fileName, InputStream fileInputStream) {
        String randomizedFileName = getNameForFile(fileName);
        upload(randomizedFileName, fileInputStream);

        return randomizedFileName;
    }

    private String getNameForFile(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID() + ext;
    }

    @Override
    public String saveFile(MultipartFile multipart) {
        String fileName = getNameForFile(multipart.getOriginalFilename());

        // todo in future: save file info into database to return files to client
        try {

            Path pathToSave = Paths.get(getStoragePath()).resolve(fileName)
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = multipart.getInputStream()) {
                Files.copy(inputStream, pathToSave,
                        StandardCopyOption.REPLACE_EXISTING);
                logger.info("Saved new file: " + pathToSave);
            }
        } catch (IOException e) {
            logger.error("Error saving file. MultiPart = " + multipart + "; Error: " + e.getMessage());
            throw new FileUploadException("Unable to upload file. Message = " + e.getMessage());
        }

        return fileName;
    }

    private String getStoragePath() {
        return Paths.get("src/main/resources/static").resolve(storageUrl).toString();     // =)
    }

    private void upload(String filename, InputStream fileInputStream) {
        try {
            String uploadPath = storageUrl;
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
