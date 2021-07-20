package com.hrAssistantBot.telegramBot.services;


import com.hrAssistantBot.telegramBot.exceptions.UserDataValidateException;
import com.hrAssistantBot.telegramBot.properties.BotConfiguration;
import com.hrAssistantBot.telegramBot.validators.UserDataValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;

import java.io.*;


public class FileService {
    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    private static final String CV_FOLDER_PATH = "candidates/cv";
    private static final String ASSIGNMENT_FOLDER_PATH = "assignments";
    private static final String ASSIGNMENT_RESULTS_FOLDER_PATH = "candidates/assignment_results";
    private static final String DOT_SIGN = ".";

    private final String projectDirectory;

    private static volatile FileService instance;


    private FileService() {
        projectDirectory = BotConfiguration.getProjectDirectory();

        File cvFolder = new File(projectDirectory + CV_FOLDER_PATH);
        createDirectory(cvFolder);

        File assignmentResultFolder = new File(projectDirectory + ASSIGNMENT_RESULTS_FOLDER_PATH);
        createDirectory(assignmentResultFolder);
    }


    public static FileService getInstance() {
        FileService localInstance = instance;
        if (localInstance == null) {
            synchronized (FileService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new FileService();
                }
            }
        }
        return instance;
    }


    public File getAssignmentFile(String fileName) {
        return new File(projectDirectory + ASSIGNMENT_FOLDER_PATH + "/" + fileName);
    }


    public String saveCvDocument(Document document) throws UserDataValidateException {
        return saveFile(document, CV_FOLDER_PATH);
    }


    public String saveAssignmentFile(Document document) throws UserDataValidateException {
        return saveFile(document, ASSIGNMENT_RESULTS_FOLDER_PATH);
    }


    private String saveFile(Document document, String folderPath) throws UserDataValidateException {
        UserDataValidator.validateDocument(document);

        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());


        File file = ExecutorService.getInstance().getFile(getFile);
        String fileName = StringUtils.substringBeforeLast(file.getName(), DOT_SIGN) + DOT_SIGN + StringUtils.substringAfterLast(document.getFileName(), DOT_SIGN);

        try (OutputStream outputStream = new FileOutputStream(projectDirectory + folderPath + "/" + fileName)) {
            byte[] data = FileUtils.readFileToByteArray(file);
            outputStream.write(data);
            return fileName;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }


    private void createDirectory(File file) {
        if (file.mkdirs()) {
            log.info("Directory [{}] successfully created", file.getAbsolutePath());
        }
    }
}
