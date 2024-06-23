package com.example.media;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class BasicService {
    @Value("${fileRepositoryFolder}")
    private String    fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID() + "." + fileType;
        File finalFolder = new File(fileRepositoryFolder);
        if (!finalFolder.exists()) throw new IOException("Folder does not exists");
        if (!finalFolder.isDirectory()) throw new IOException("Folder is not a directory");
        File finalFile = new File(fileRepositoryFolder + "\\" + newFileName);
        if (finalFile.exists()) throw new IOException("File conflict");
        file.transferTo(finalFile);
        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File toDownload = new File(fileRepositoryFolder + "\\" + fileName);
        if(!toDownload.exists()) throw  new IOException("File does not exists");
        return IOUtils.toByteArray(new FileInputStream(toDownload));
    }
}
