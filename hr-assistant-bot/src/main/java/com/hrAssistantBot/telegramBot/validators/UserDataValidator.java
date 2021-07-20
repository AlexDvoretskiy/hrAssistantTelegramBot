package com.hrAssistantBot.telegramBot.validators;


import com.hrAssistantBot.telegramBot.exceptions.UserDataValidateException;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.api.objects.Document;


public class UserDataValidator {
    private static final Logger log = LoggerFactory.getLogger(UserDataValidator.class);

    private static final String EMPTY_FILE_ERROR_MESSAGE = "Пожалуста, отправьте корректный файл";
    private static final String FILE_NAME_PATTERN = "^.*\\.(do(c|cx)|pdf)$";


    public static void validateDocument(Document document) throws UserDataValidateException {
        if (document == null || StringUtils.isEmpty(document.getFileId()))
            throw new UserDataValidateException(EMPTY_FILE_ERROR_MESSAGE);
    }
}
