package com.blameo.trello.Util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUpload {
    public final String DIR_LOAD = "E:\\Blameo\\trello\\src\\main\\resources\\static\\image";

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Path path = Paths.get(DIR_LOAD + File.separator + multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

    public Resource downloadFile(String fileName) {

        Path path = Paths.get(DIR_LOAD).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }


}
