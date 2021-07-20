package com.hrAssistantBot.telegramBot.services;


import com.hrAssistantBot.telegramBot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;


public class ExecutorService {
    private static final Logger log = LoggerFactory.getLogger(ExecutorService.class);

    private final TelegramBot telegramBot;

    private ExecutorService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    private static volatile ExecutorService instance;

    public static void createInstance(TelegramBot telegramBot) {
        ExecutorService localInstance = instance;
        if (localInstance == null) {
            synchronized (ExecutorService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new ExecutorService(telegramBot);
                }
            }
        }
    }

    public static ExecutorService getInstance() {
        return instance;
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения пользователю: " + e.getMessage());
        }
    }

    public void sendDocument(SendDocument sendDocument) {
        try {
            telegramBot.execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке документа пользователю: " + e.getMessage());
        }
    }

    public File getFile(GetFile getFile) {
        try {
            String filePath = telegramBot.execute(getFile).getFilePath();
            return telegramBot.downloadFile(filePath);
        } catch (TelegramApiException e) {
            log.error("Ошибка при получении файла пользователя: " + e.getMessage());
            return null;
        }
    }
}
