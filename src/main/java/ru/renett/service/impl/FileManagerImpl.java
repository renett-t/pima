package ru.renett.service.impl;

import org.springframework.stereotype.Service;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.FileUploadException;
import ru.renett.service.file.FileManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileManagerImpl implements FileManager {
    private final String storageUrl;

    public FileManagerImpl() {
        this.storageUrl = Constants.STORAGE_URL;
    }

    @Override
    public String saveFile(String fileName, InputStream fileInputStream) {
        String randomizedFileName = getNameForFile(fileName);
        upload(randomizedFileName, fileInputStream);

        return randomizedFileName;
    }

    private String getNameForFile(String fileName) {
        return UUID.randomUUID() + fileName;
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
    // MultipartFile multipartFile = form.getFile();
    //        String filename = UUID.randomUUID().toString() + ".png";
    //        Path pathToSave = Paths.get(repositoryPath).resolve(filename);
    //
    //        File file = new File(String.valueOf(pathToSave));
    //        try {
    //            file.createNewFile();
    //            multipartFile.transferTo(file);
    //        } catch (IOException ioException) {
    //            log.error(ioException.getMessage());
    //        }
    //        return filename;
}
