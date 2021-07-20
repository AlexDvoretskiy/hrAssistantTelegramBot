package com.hrAssistantWeb.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class FileService {

    @Value("${project.directory}")
    private String projectDirectory;
    @Value("${project.cv_directory}")
    private String cvDirectory;
    @Value("${project.assignment_directory}")
    private String assignmentDirectory;
    @Value("${project.assignment_result_directory}")
    private String assignmentResultDirectory;


    public File getAssignmentResultFile(String fileName) {
        return new File(projectDirectory + assignmentResultDirectory + "/" + fileName);
    }

    public File getAssignmentFile(String fileName) {
        return new File(projectDirectory + assignmentDirectory + "/" + fileName);
    }

    public void saveAssignmentFile(MultipartFile multipartFile) {
        String path = projectDirectory + assignmentDirectory + "/" + multipartFile.getOriginalFilename();
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCvFile(String fileName) {
        return new File(projectDirectory + cvDirectory + "/" + fileName);
    }
}
